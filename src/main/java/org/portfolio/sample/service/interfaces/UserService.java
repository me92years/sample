package org.portfolio.sample.service.interfaces;

import org.portfolio.sample.dto.UserDTO;

public interface UserService {
	
	public boolean checkNickname(UserDTO userDTO);
	public boolean updateNickname(UserDTO userDTO);
}
