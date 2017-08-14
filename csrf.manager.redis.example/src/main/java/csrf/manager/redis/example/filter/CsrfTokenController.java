package csrf.manager.redis.example.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import csrf.manager.redis.example.csrf.CsrfTokenHolder;
import csrf.manager.redis.example.redis.RedisClientManager;
import csrf.manager.redis.example.redis.RedisDataManager;

public class CsrfTokenController implements Filter {

	private ThreadLocal<RedisDataManager> redisDataManagerHolder;
	
	
	@Override
	public void destroy() {
		
		this.redisDataManagerHolder = null;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		
		
		HttpSession session = httpServletRequest.getSession(true);
		
		Object springSecurityContext = session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		if(springSecurityContext == null) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		

		String sessionId = httpServletRequest.getSession().getId();
		
		RedisDataManager redisDataManager = redisDataManagerHolder.get();
		
		
		CsrfTokenHolder csrfTokenHolder = redisDataManager.get(sessionId);
		
		String requestId = (String) httpServletRequest.getParameter("requestId");
		
		if(csrfTokenHolder.getCsrfTokenList().contains(requestId)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		throw new ServletException("CSRF invalid token exception!");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		this.redisDataManagerHolder = new ThreadLocal<RedisDataManager>() {
			@Override
			protected RedisDataManager initialValue() {
				
				return new RedisDataManager(RedisClientManager.getRedisClient());
				
			}
		};
	}
	
}
