package org.portfolio.sample.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.portfolio.sample.dto.UserDTO;
import org.portfolio.sample.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@javax.persistence.Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Table(name = "users")
public class User extends Base {
	
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = true)
	private String picture;
	
	@Column(nullable = true, unique = true)
	private String nickname;
	
	@Column(nullable = true, length = 500)
	private String refresh;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public UserDTO toDTO() {
		return new UserDTO(this);
	}
	
	public User entityUpdate(String picture) {
		this.picture = picture;
		return this;
	}
	
	public User refreshUpdate(String refresh) {
		this.refresh = refresh;
		return this;
	}
	
	public User nicknameUpdate(String nickname) {
		this.nickname = nickname;
		return this;
	}
	
}
