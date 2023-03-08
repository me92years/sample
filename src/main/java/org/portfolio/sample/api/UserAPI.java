package org.portfolio.sample.api;

import java.net.URI;

import org.portfolio.sample.dto.UserDTO;
import org.portfolio.sample.service.interfaces.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserAPI {
	
	private final UserService userService;
	
	@GetMapping("/apix/session/user")
	public ResponseEntity<Object> loginUser(@AuthenticationPrincipal UserDTO dto) {
		return new ResponseEntity<>(dto, HttpStatus.OK); 
	}
	
	@GetMapping("/apio/user/logout")
	public ResponseEntity<Object> logoutUser() {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("http://localhost:3000/"));
		return new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);
	}
	
	@PostMapping("/apix/update/nickname")
	public ResponseEntity<Boolean> updateNickname(@RequestBody UserDTO userDTO) {
		boolean result = userService.updateNickname(userDTO);
		return new ResponseEntity<>(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/apix/check/nickname")
	public ResponseEntity<Boolean> checkNickname(UserDTO userDTO) {
		return null;
	}
	
	
	
}
