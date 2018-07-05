package banking.security.application;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import banking.common.application.Notification;
import banking.common.application.enumeration.RequestBodyType;
import banking.security.application.dto.CredentialInputDto;
import banking.security.application.dto.JwTokenOutputDto;
import banking.security.domain.entity.User;
import banking.security.domain.repository.UserRepository;

@Named
public class UserApplicationService {
	
	// get base64 encoded version of the key
	public static final String JWt_ENCODED_KEY = "k8zgjphoSZl4aTtNKiOXMQ==";

	@Inject
	UserRepository userRepository;

	@Transactional
	public JwTokenOutputDto verifyUser(CredentialInputDto dto) {
		
		Notification notification = this.validation(dto);
		if (notification.hasErrors()) {
			throw new IllegalArgumentException(notification.errorMessage());
		}
		
		String username = dto.getUserName();
		String password = dto.getPassword();
		
		
		return validateUser(username, password);
	} 
	
	@Transactional
	public JwTokenOutputDto verifyUser(String username, String password) {
		
		Notification notification = this.validation(username, password);
		if (notification.hasErrors()) {
			throw new IllegalArgumentException(notification.errorMessage());
		}
		
		return validateUser(username, password);
	}

	private JwTokenOutputDto validateUser(String username, String password) {
		User user = userRepository.findByUserName(username);
		
		if(user == null) {
			
			return null;
		}

		if(!user.verifyIdentity(password)) {
			
			throw new IllegalArgumentException("Verify credential");
		};
		
		
		
		String accessToken = user.generateJWT(JWt_ENCODED_KEY);
		
		JwTokenOutputDto AccessTokenResponse = new JwTokenOutputDto(accessToken, "jwt", 36000, accessToken,
				user.getUserRole().toString());
		
		
		
		
		return AccessTokenResponse;
	} 
	
	private Notification validation(String username, String password) {
		Notification notification = new Notification();
		if (StringUtils.isEmpty(username)) {
			notification.addError("Username is empty.");
		}
		
		if (StringUtils.isEmpty(password)) {
			notification.addError("Password is empty.");
		}
		return notification;
	}

	private Notification validation(CredentialInputDto requestBankTransferDto) {
		Notification notification = new Notification();
		if (requestBankTransferDto == null || requestBankTransferDto.getRequestBodyType() == RequestBodyType.INVALID) {
			notification.addError("Invalid JSON data in request body.");
		}
		return notification;
	}

}
