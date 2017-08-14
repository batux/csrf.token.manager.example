package csrf.manager.redis.example.filter;

import java.io.IOException;
import java.util.UUID;

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

public class CsrfTokenGenerator implements Filter {

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
		
		String requestId = UUID.randomUUID().toString().replaceAll("-", "");
		
		if(!csrfTokenHolder.getCsrfTokenList().contains(requestId)) {
			
			csrfTokenHolder.getCsrfTokenList().add(requestId);
		}
		
		
		boolean recordSaveStatus = redisDataManager.put(sessionId, csrfTokenHolder);
		
		if(recordSaveStatus) {
			
			httpServletRequest.setAttribute("requestId", requestId);
			
			filterChain.doFilter(servletRequest, servletResponse);
		}
		else {
			throw new ServletException("CSRF checking process was failed!");
		}
		
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
