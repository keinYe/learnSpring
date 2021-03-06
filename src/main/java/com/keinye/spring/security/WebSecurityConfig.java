package com.keinye.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Configuration
	@Order(1)
	class PredictorSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(new TokenAuthenticationProvider());
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/API")
				.addFilterAfter(new TokenAuthenticationFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new ResultExceptionTranslationFilter(), ExceptionTranslationFilter.class)
				.authorizeRequests()
				.anyRequest().hasRole("API")
				.and()
				.csrf().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
	}
	
	@Configuration
	@Order(2)
	class PathTokeSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
            http
            .antMatcher("/api/v1s")
            .authorizeRequests()
            .antMatchers("/api/v1")
            .access("@pathValidator.ensureHash(authentication, #user, request.getParameter(\"hash\"))")
            .and()
            .csrf()
            .disable() // need to disable csrf
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
	}
	
	@Configuration
	@Order(3)
	class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(customUserDetailService);
		}
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/api/ignore");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// TODO 自动生成的方法存根
			super.configure(http);
		}
		
		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName(CSRFUtil.CSRF_TOKEN_NAME);
			repository.setHeaderName(CSRFUtil.CSRF_ATTRIBUTE_NAME);
			return new LazyCsrfTokenRepository(repository);
		}
		
		private JsonAuthenticationFilter authenticationFilter() throws Exception {
			JsonAuthenticationFilter filter = new JsonAuthenticationFilter();
			filter.setAuthenticationManager(authenticationManager());
			return filter;
		}
	}

	
}
