package quote.project.accounting.service;
 
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quote.project.accounting.configuration.UserAccountConfiguration;
import quote.project.accounting.dao.UserRepository;
import quote.project.accounting.dto.UserProfileDto;
import quote.project.accounting.dto.UserRegisterDto;
import quote.project.accounting.exceptions.ConflictException;
import quote.project.accounting.exceptions.NotFoundException;
import quote.project.accounting.model.UserAccount;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserAccountConfiguration userConfiguration;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public UserProfileDto register(UserRegisterDto userRegisterDto) {
		if (userRepository.existsById(userRegisterDto.getLogin())) {
			throw new ConflictException();
		}
		String hashPassword = passwordEncoder.encode(userRegisterDto.getPassword());
		UserAccount userAccount = UserAccount.builder()
				.login(userRegisterDto.getLogin())
				.password(hashPassword)
				.role("User")
				.build();
		return userAccountToUserProfileDto(userRepository.save(userAccount));
	}

	private UserProfileDto userAccountToUserProfileDto(UserAccount userAccount) {		
		return UserProfileDto.builder()
				.login(userAccount.getLogin())
				.roles(userAccount.getRoles())
				.build();
	}

	@Override
	public UserProfileDto login(String login) {
		UserAccount userAccount = userRepository.findById(login).get();
		return userAccountToUserProfileDto(userAccount);
	}

	@Transactional
	@Override
	public UserProfileDto removeUser(String login) {
		UserAccount userAccount = userRepository.findById(login).get();
		userRepository.deleteById(login);
		return userAccountToUserProfileDto(userAccount);
	}

	@Transactional
	@Override
	public void changePassword(String login, String password) {
		UserAccount userAccount = userRepository.findById(login).get();
		String hashPassword = passwordEncoder.encode(password);
		if (hashPassword.equals(userAccount.getPassword())) {
			throw new ConflictException();
		}
		userAccount.setPassword(hashPassword);
	}

	@Transactional
	@Override
	public Set<String> addRole(String login, String role) {
		UserAccount userAccount = userRepository.findById(login).orElseThrow(NotFoundException::new);
		userAccount.addRole(role);
		return userAccount.getRoles();
	}

	@Transactional
	@Override
	public Set<String> removeRole(String login, String role) {
		UserAccount userAccount = userRepository.findById(login).orElseThrow(NotFoundException::new);
		userAccount.removeRole(role);
		return userAccount.getRoles();
	}

}
