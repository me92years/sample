package org.portfolio.sample.service;

import javax.transaction.Transactional;

import org.portfolio.sample.dto.UserDTO;
import org.portfolio.sample.entity.User;
import org.portfolio.sample.repository.UserRepository;
import org.portfolio.sample.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService  {

	private final UserRepository userRepository;
	
	@Override
	@Transactional
	public boolean updateNickname(UserDTO userDTO) {
		try {
			User user = userRepository
					.getByPK(userDTO.getId())
					.map(en -> en.nicknameUpdate(userDTO.getNickname()))
					.get();
			userRepository.save(user);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean checkNickname(UserDTO userDTO) {
		
		return false;
	}

	
	
}
