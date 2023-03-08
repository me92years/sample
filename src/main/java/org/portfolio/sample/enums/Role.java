package org.portfolio.sample.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
	
	USER("ROLE_USER", "일반"),
	ADMIN("ROLE_ADMIN", "관리")
	;
	
	private final String key;
	private final String title;
	
}
