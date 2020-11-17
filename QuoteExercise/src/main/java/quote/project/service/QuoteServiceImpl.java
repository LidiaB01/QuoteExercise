package quote.project.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quote.project.dao.ItemRepository;
import quote.project.dao.LogRepository;
import quote.project.dao.QuoteRepository;
import quote.project.dto.ItemDto;
import quote.project.dto.QuoteDto;
import quote.project.exceptions.QuoteExistException;
import quote.project.exceptions.QuoteNotFoundException;
import quote.project.model.Item;
import quote.project.model.Operation;
import quote.project.model.Quote;
import quote.project.model.QuoteLog;

@Service
public class QuoteServiceImpl implements QuoteService {

	@Autowired
	QuoteRepository quoteRepository;
	@Autowired
	LogRepository logRepository;
	@Autowired
	ItemRepository itemRepository;
	

	@Override
	@Transactional
	public QuoteDto addQuote(QuoteDto quoteDto){
		if (quoteRepository.existsById(quoteDto.getName())) { 			
			throw new QuoteExistException();
		}
		List<Item> items = quoteDto.getItems().stream()
				.map(i -> itemRepository.findById(i.getId()).orElseGet(() -> itemRepository.save(new Item(i.getId(), i.getName()))))
				.collect(Collectors.toList());
		
		Quote quote = Quote.builder()
				.name(quoteDto.getName())
				.price(quoteDto.getPrice())
				.items(items)
				.build();		
		quoteRepository.save(quote);
		return quoteToQuoteDto(quote);
	}

	private QuoteDto quoteToQuoteDto(Quote quote) {		
		return QuoteDto.builder()
				.name(quote.getName())
				.price(quote.getPrice())
				.items(quote.getItems().stream().map(i -> itemToItemDto(i)).collect(Collectors.toList()))
				.build();
	}


	private ItemDto itemToItemDto(Item item) {		
		return ItemDto.builder()
				.id(item.getId())
				.name(item.getName())
				.build();
	}

	@Override
	public QuoteDto getQuote(String name) {	
		Quote quote = quoteRepository.findByNameAndStateFalse(name).orElseThrow(QuoteNotFoundException::new);
		return quoteToQuoteDto(quote);
	}

	@Transactional
	@Override
	public QuoteDto updateQuote(String name, QuoteDto quoteDto) {
		Quote quote = quoteRepository.findByNameAndStateFalse(name).orElseThrow(QuoteNotFoundException::new);
		quote.setPrice(quoteDto.getPrice());		
		if (quoteDto.getItems() != null) {
			List<Item> items = quoteDto.getItems().stream()
					.map(i -> itemRepository.findById(i.getId()).orElseGet(() -> itemRepository.save(new Item(i.getId(), i.getName()))))
					.collect(Collectors.toList());
			quote.setItems(items);
		}
		return quoteToQuoteDto(quote);
	}
	
	@Transactional
	@Override 
	public QuoteDto deleteQuote(String name) {
		Quote quote = quoteRepository.findByNameAndStateFalse(name).orElseThrow(QuoteNotFoundException::new);
		quoteRepository.delete(quote);
		return quoteToQuoteDto(quote);
	}

	public List<QuoteLog> getLogs() {
		return logRepository.findAll();
	}
	
	
	@Transactional
	public void perform(String name, Operation operation) {
		QuoteLog log = QuoteLog.builder()
				.quoteId(name)
				.createdDate(LocalDate.now())
				.operation(operation)
				.build();		
		logRepository.save(log);	
	}
	
	@Transactional
	public void perform(String name, Operation operation, Throwable e) {
		QuoteLog log = QuoteLog.builder()
				.quoteId(name)
				.createdDate(LocalDate.now())
				.operation(operation)
				.errorCode(1)
				.message(e.getMessage())
				.build();		
		logRepository.save(log);
	}
}
