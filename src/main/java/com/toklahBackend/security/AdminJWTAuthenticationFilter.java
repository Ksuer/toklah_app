package com.toklahBackend.security;

import static com.toklahBackend.security.SecurityConstants.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class AdminJWTAuthenticationFilter extends OncePerRequestFilter {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final AdminDetailsServiceImp adminDetailsService;
	private final JwtTokenUtil jwtTokenUtil;
	private final String tokenHeader;

	public AdminJWTAuthenticationFilter(AdminDetailsServiceImp adminDetailsService, JwtTokenUtil jwtTokenUtil,
			@Value(HEADER_STRING) String tokenHeader) {
		this.adminDetailsService = adminDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.tokenHeader = tokenHeader;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		logger.info("processing authentication for '{}'", request.getRequestURL());

		final String requestHeader = request.getHeader(this.tokenHeader);

		String username = null;
		String authToken = null;

		if (requestHeader != null && requestHeader.startsWith(TOKEN_PREFIX)) {
			authToken = requestHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
			}
		} else {
			logger.warn("couldn't find bearer string, will ignore the header");
		}

		logger.debug("checking authentication for user '{}'", username);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			logger.debug("security context was null, so authorizating user");

			UserDetails userDetails = this.adminDetailsService.loadUserByUsername(username);
		
			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				logger.info("authorizated user '{}', setting security context", username);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}

}