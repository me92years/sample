package org.portfolio.sample.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.portfolio.sample.dto.OAuth2AttributesDTO;
import org.portfolio.sample.dto.UserDTO;
import org.portfolio.sample.entity.User;
import org.portfolio.sample.enums.Role;
import org.portfolio.sample.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		String clientName = userRequest.getClientRegistration().getClientName();
		String nameAttributeKey = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		if (!clientName.equals("Google")) return null;
		OAuth2AttributesDTO attrDTO = new OAuth2AttributesDTO(delegate.loadUser(userRequest));
		UserDTO user = saveOrUpdate(attrDTO);
		
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		request.setAttribute("user", user);
		
		return new DefaultOAuth2User(user.getAuthorities(), attrDTO.getAttributes(), nameAttributeKey);
	}
	
	@Transactional
  private UserDTO saveOrUpdate(OAuth2AttributesDTO attrDTO) {
  	String email = attrDTO.getEmail();
  	User user = userRepository.getByEmail(email).map(en -> en.entityUpdate(attrDTO.getPicture())).orElse(null);
  	if (user != null) {
  		return new UserDTO(user);
  	} else {
  		User newUser = User.builder()
  				.email(email)
  				.picture(attrDTO.getPicture())
  				.nickname(null)
  				.role(email.equals("lapislazuli.des@gmail.com") ? Role.ADMIN : Role.USER)
  				.build();
  		return new UserDTO(userRepository.save(newUser));
  	}
  }

}
