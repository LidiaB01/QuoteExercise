package quote.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import quote.project.accounting.dao.UserRepository;
import quote.project.accounting.model.UserAccount;

@SpringBootApplication
public class QuoteProjectApplication implements CommandLineRunner{
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(QuoteProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!userRepository.existsById("admin")) {
			String hashPassword = passwordEncoder.encode("admin");
			UserAccount admin = UserAccount.builder()
					.login("admin")
					.password(hashPassword)
					.role("User")
					.role("Administrator")				
					.build();
			userRepository.save(admin);
		}		
	}
}
