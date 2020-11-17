package quote.project.service;


import java.util.List;

import quote.project.dto.QuoteDto;
import quote.project.model.Operation;
import quote.project.model.QuoteLog;

public interface QuoteService {
	QuoteDto addQuote (QuoteDto quoteDto);
	QuoteDto getQuote (String name);
	QuoteDto updateQuote (String name, QuoteDto quoteDto);
	QuoteDto deleteQuote (String name);	
	void perform(String name, Operation create);
	void perform(String name, Operation operation, Throwable e);
	List<QuoteLog> getLogs();

}
