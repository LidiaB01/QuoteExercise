package quote.project.accounting.service;

import java.util.Set;

import quote.project.accounting.dto.UserProfileDto;
import quote.project.accounting.dto.UserRegisterDto;

public interface UserAccountService {
	UserProfileDto register(UserRegisterDto userRegisterDto);
	UserProfileDto login(String token); 
	UserProfileDto removeUser(String token);
	void changePassword(String token, String password);
	Set<String> addRole(String login, String role);
	Set<String> removeRole(String login, String role);
}
