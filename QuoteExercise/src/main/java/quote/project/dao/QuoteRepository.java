package quote.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quote.project.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, String>{

	@Query("select q from Quote q where q.name = ?1 and q.state = false")
	Optional<Quote> findByNameAndStateFalse(String name);
}
