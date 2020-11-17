package quote.project.accounting.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quote.project.accounting.dto.UserProfileDto;
import quote.project.accounting.dto.UserRegisterDto;
import quote.project.accounting.service.UserAccountService;

@RestController
@RequestMapping("/account")
public class UserAccountController {
	@Autowired
	UserAccountService userAccountService;
	
	@PostMapping("/user")
	public UserProfileDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return userAccountService.register(userRegisterDto);
	}
	
	@PostMapping("/login")
	public UserProfileDto login(Authentication authentication) {
		return userAccountService.login(authentication.getName());
	}
	
	@DeleteMapping("/user")
	public UserProfileDto removeUser(Authentication authentication) {
		return userAccountService.removeUser(authentication.getName());
	}

	@PutMapping("/user/password")
	public void changePassword(Authentication authentication, @RequestHeader ("X-Password") String password) {
		userAccountService.changePassword(authentication.getName(), password);
	}
	
	@PostMapping("/{login}/role/{role}")
	public Set<String> addRole(@PathVariable String login, @PathVariable String role) {
		return userAccountService.addRole(login, role);
	}
	
	@DeleteMapping("/{login}/role/{role}")
	public Set<String> removeRole(@PathVariable String login, @PathVariable String role) {
		return userAccountService.removeRole(login, role);
	}
}
