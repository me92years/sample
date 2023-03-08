package org.portfolio.sample.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OAuth2AttributesDTO {

	private Map<String, Object> attributes;
	private	Collection<? extends GrantedAuthority> authorities;
	private String email;
	private String picture;
	
	public OAuth2AttributesDTO(OAuth2User oAuth2User) {
		this.attributes = oAuth2User.getAttributes();
		this.authorities = oAuth2User.getAuthorities();
		this.email = oAuth2User.getAttribute("email");
		this.picture = oAuth2User.getAttribute("picture");
	}
	
}
