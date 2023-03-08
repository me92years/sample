package org.portfolio.sample.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.portfolio.sample.dto.UserDTO;
import org.portfolio.sample.util.JWTUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
	
	private final JWTUtil jwtUtil;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserDTO dto = (UserDTO) request.getAttribute("user");
		String access = jwtUtil.createToken(dto);
		response.addHeader("Set-Cookie", "Access"+ dto.getId()  +"=" + access + "; path=/; HttpOnly; Secure; SameSite=Strict;");
		response.sendRedirect("http://localhost:3000");
	}

}
