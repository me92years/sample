package org.portfolio.sample.config;

import org.portfolio.sample.filter.AuthenticationLogoutFilter;
import org.portfolio.sample.filter.JWTAuthenticationFilter;
import org.portfolio.sample.handler.OAuthSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final OAuthSuccessHandler oauthSuccessHandler;
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationLogoutFilter authenticationLogoutFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtAuthenticationFilter, OAuth2LoginAuthenticationFilter.class);
		http.addFilterAt(authenticationLogoutFilter, JWTAuthenticationFilter.class);
		http
			.headers()
				.frameOptions().disable()
				.and()
			.authorizeHttpRequests()
				.antMatchers("/apio/**/*").permitAll()
				.antMatchers("/apix/**/*").hasAnyRole("USER", "ADMIN")
				.antMatchers("/apixx/**/*").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
			.oauth2Login()
				.successHandler(oauthSuccessHandler)
				.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.logout().disable()		
			.csrf().disable()
			.httpBasic().disable()
			.formLogin().disable();
		return http.build();
	}

}
