package org.portfolio.sample.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.portfolio.sample.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationLogoutFilter extends OncePerRequestFilter {
	
	private final JWTUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = null;
		if ((header = request.getHeader("Cookie")) != null) {
			Set<String> cookies = Set.of(header.split("; "));
			String access = jwtUtil.checkCookies(cookies, "Access");
			String jsessionID = jwtUtil.checkCookies(cookies, "JSESSIONID");
			if (access != null) {
				access = access.substring(0, access.indexOf("="));
				response.addHeader("Set-Cookie", access + "=none; path=/; HttpOnly; Secure; SameSite=Strict; expires=Thu, 01 Jan 1970 00:00:01 GMT;");
			}
			if (jsessionID != null) {
				jsessionID = jsessionID.substring(0, jsessionID.indexOf("="));
				response.addHeader("Set-Cookie", jsessionID + "=none; path=/; domain=localhost; expires=Thu, 01 Jan 1970 00:00:01 GMT;");
			}
		}
		
		doFilter(request, response, filterChain);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !"/apio/user/logout".equalsIgnoreCase(request.getServletPath());
	}
	
	
}
