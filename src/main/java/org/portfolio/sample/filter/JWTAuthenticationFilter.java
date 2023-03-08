package org.portfolio.sample.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.portfolio.sample.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;
  private static final List<String> EXCLUDE_URL = 
  		Collections.unmodifiableList(
  				Arrays.asList(
  						"/oauth2/authorization/google",
  						"/apio/user/logout"
  						));
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = null;
		if ((header = request.getHeader("Cookie")) != null) {
			Set<String> cookies = Set.of(header.split("; "));
			String access = jwtUtil.checkCookies(cookies, "Access");
			if (access != null) {
				access = access.substring(access.indexOf("=") + 1);
				DefaultClaims claims = null;
				if ((claims = jwtUtil.validateOrExtract(access, request)) != null) {
					String newAccess = jwtUtil.updateByClaims(claims);
					response.addHeader("Set-Cookie", "Access"+ claims.get("id") + "=" + newAccess + "; path=/; HttpOnly; Secure; SameSite=Strict;");
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
	}

}
