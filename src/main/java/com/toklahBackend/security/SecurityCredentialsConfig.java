package com.toklahBackend.security;


import static com.toklahBackend.security.SecurityConstants.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity 	
@Order(Ordered.HIGHEST_PRECEDENCE)
class AdminSecurityCredentialsConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	@Qualifier("customAdminDetailsService")
	private UserDetailsService adminDetailsService;
	
	@Autowired
	AdminJWTAuthenticationFilter JWTAuthenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().antMatchers("/admin/**")
		
		    .and().csrf().disable()
		     // make sure we use stateless session; session won't be used to store user's state.
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	            // handle an authorized attempts 
	            .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
	        .and()
	        .addFilterBefore(JWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	        .authorizeRequests()
	
		    // allow requests 
		    .antMatchers(ADMIN_LOGIN,ADMIN_SIGN_UP_URL,ADMIN_forgotPassword,
		    		//for swagger
		    		"/v2/api-docs",
	                "/configuration/ui",
	                "/swagger-resources/**",
	                "/configuration/security",
	                "/swagger-ui.html",
	                "/webjars/**").permitAll()
		    // any other requests must be authenticated
		    .anyRequest().authenticated()
		    ;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(adminDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}

@Configuration
@EnableWebSecurity
class SecurityCredentialsConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	@Autowired
	@Qualifier("customAdminDetailsService")
	private UserDetailsService adminDetailsService;
	
	@Autowired
	JWTAuthenticationFilter JWTAuthenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .csrf().disable()
		     // make sure we use stateless session; session won't be used to store user's state.
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	            // handle an authorized attempts 
	            .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
	        .and()

	        .addFilterBefore(JWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		
		    // allow requests 
		    .antMatchers(SIGN_UP_URL,FORGOTPASSWORD,FORGOTUSERNAME,LOGIN,
		    		//for swagger
		    		"/v2/api-docs",
	                "/configuration/ui",
	                "/swagger-resources/**",
	                "/configuration/security",
	                "/swagger-ui.html",
	                "/webjars/**").permitAll()
		    // any other requests must be authenticated
		    .anyRequest().authenticated();
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}

