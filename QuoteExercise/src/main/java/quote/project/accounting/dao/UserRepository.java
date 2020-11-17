package quote.project.accounting.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import quote.project.accounting.model.UserAccount;

public interface UserRepository extends JpaRepository<UserAccount, String>{

}
