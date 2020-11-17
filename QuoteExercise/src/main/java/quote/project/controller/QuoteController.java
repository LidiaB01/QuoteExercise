package quote.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quote.project.dto.QuoteDto;
import quote.project.model.Operation;
import quote.project.model.QuoteLog;
import quote.project.service.QuoteService;

@RestController
@RequestMapping("/quote")
public class QuoteController {
	@Autowired
	QuoteService quoteService;
	
	@PostMapping
	public QuoteDto addQuote(@RequestBody QuoteDto quoteDto, HttpServletRequest request) {		
		request.setAttribute("name", quoteDto.getName());
		QuoteDto responseDto = quoteService.addQuote(quoteDto);
		quoteService.perform(quoteDto.getName(), Operation.CREATE);
		return responseDto;
	}
	
	@GetMapping ("/name/{name}")
	public QuoteDto getQuote(@PathVariable String name) {	
		return quoteService.getQuote(name);
	}

	@PutMapping ("/name/{name}")
	public QuoteDto updateQuote(@PathVariable String name, @RequestBody QuoteDto quoteDto, HttpServletRequest request) {
		request.setAttribute("name", name);
		QuoteDto responseDto = quoteService.updateQuote(name, quoteDto);
		quoteService.perform(name, Operation.UPDATE);
		return responseDto;
	}
	
	@DeleteMapping ("/name/{name}")
	public QuoteDto deleteQuote(@PathVariable String name, HttpServletRequest request) {
		request.setAttribute("name", name);
		QuoteDto responseDto = quoteService.deleteQuote(name);
		quoteService.perform(name, Operation.DELETE);
		return responseDto;
	}
	
	@GetMapping("/logs")
	public List<QuoteLog> getLogs() {
		return quoteService.getLogs();
	}
}
