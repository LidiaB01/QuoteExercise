package quote.project.accounting.configuration;

import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import quote.project.accounting.exceptions.UserAuthentificationException;
import quote.project.accounting.service.security.UserAccountCredentials;

@Configuration
public class UserAccountConfiguration {	
	public UserAccountCredentials tokenDecode(String token) {
		try {
			int pos = token.indexOf(" ");
			token = token.substring(pos + 1);
			String credential = new String(Base64.getDecoder().decode(token));
			String[] credentials = credential.split(":");
			return new UserAccountCredentials(credentials[0], credentials[1]);
		} catch (Exception e) {
			throw new UserAuthentificationException();
		}
	}

	@Bean
	public PasswordEncoder	getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

