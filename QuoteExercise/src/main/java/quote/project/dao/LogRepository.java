package quote.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import quote.project.model.QuoteLog;

public interface LogRepository extends JpaRepository<QuoteLog, Long> {

}
