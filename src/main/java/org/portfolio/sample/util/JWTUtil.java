package org.portfolio.sample.util;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.portfolio.sample.dto.UserDTO;
import org.portfolio.sample.entity.User;
import org.portfolio.sample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTUtil {

	@Value("${spring.custom.secret-key}")
	private String secretKey;
	
	private long ACCESS_TOKEN_EXPIRATION = 120;
	private long REFRESH_TOKEN_EXPIRATION = 120;
	
	private final UserRepository userRepository;

	public String createToken(UserDTO dto) {
		String access = null, refresh = null;
		try {
			access = create(dto, ACCESS_TOKEN_EXPIRATION);
			refresh = create(dto, REFRESH_TOKEN_EXPIRATION);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		updateRefresh(dto, refresh);
		return access;
	}

	public String updateByClaims(DefaultClaims claims) {
		String email = claims.getSubject();
		UserDTO userDTO = userRepository.getByEmail(email).map(en -> en.toDTO()).get();
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDTO, "", userDTO.getAuthorities()));
		return createToken(userDTO);
	}

	@Transactional
	private void updateRefresh(UserDTO dto, String refresh) {
		User user = userRepository.getByEmail(dto.getEmail()).map(en -> en.refreshUpdate(refresh)).get();
		userRepository.save(user);
	}

	private String create(UserDTO dto, long ACCESS_TOKEN_EXPIRATION) throws UnsupportedEncodingException {
		String access = null;
		access = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(ACCESS_TOKEN_EXPIRATION).toInstant()))
				.setSubject(dto.getEmail())
				.claim("id", dto.getId())
				.claim("picture", dto.getPicture())
				.claim("nickname", dto.getNickname())
				.claim("role", dto.getAuthorities().toString())
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("utf-8"))
				.compact();
		return access;
	}

	public DefaultClaims validateOrExtract(String access, HttpServletRequest request) {
		DefaultClaims defaultClaims = null;
		try {
			defaultClaims = (DefaultClaims) Jwts.parser()
					.setSigningKey(secretKey.getBytes("utf-8"))
					.parseClaimsJws(access).getBody();
		} catch (
					ExpiredJwtException 
				| UnsupportedJwtException 
				| MalformedJwtException
				| SignatureException
				| IllegalArgumentException 
				| UnsupportedEncodingException e) {
			if (e.getClass().equals(ExpiredJwtException.class) || e.getClass().equals(SignatureException.class)) {
				request.setAttribute("exception", "ExpiredJwt");
			} else {
				request.setAttribute("exception", e.getClass().getSimpleName());
			}
			
		}
		return defaultClaims;
	}

	public String checkCookies(Set<String> cookies, String key) {
		return cookies.stream().filter(c -> c.startsWith(key)).findFirst()
		.orElse(null);
	}
	
}
