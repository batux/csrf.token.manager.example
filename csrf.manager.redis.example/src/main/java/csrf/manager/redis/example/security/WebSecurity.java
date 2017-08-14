package csrf.manager.redis.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

	
	@Autowired
	@Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService);
		auth.inMemoryAuthentication().withUser("batux").password("batux").roles("USER");
	}
	
	
	@Override
	public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
		
		 web.ignoring().antMatchers("/csrf.manager.redis.example/login");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.csrf().disable()
		.authorizeRequests()
			.anyRequest()
				.authenticated()
				.antMatchers("/csrf.manager.redis.example/**").permitAll()
				.and()
		        .requestCache()
		        .requestCache(new NullRequestCache())
		    .and()
			    .formLogin()
			    .loginProcessingUrl("/login")
			    .successHandler(new SigninSuccessHandler())
			    .permitAll();
		
	}
}
