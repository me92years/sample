package org.portfolio.sample.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.portfolio.sample.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String email;
	
	private String picture;
	
	private String nickname;
	
	private String role;
	
	private Collection<? extends GrantedAuthority> authorities;

	public UserDTO(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.picture = user.getPicture();
		this.nickname = user.getNickname();
		this.role = user.getRole().name();
		this.authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey()));
	}

}
