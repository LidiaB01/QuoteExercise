package quote.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import quote.project.exceptions.QuoteExistException;
import quote.project.exceptions.IllegalNameException;
import quote.project.exceptions.NegativeArgumentException;
import quote.project.exceptions.QuoteNotFoundException;
import quote.project.model.Operation;
import quote.project.service.QuoteService;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
	private static final String LEVEL_ERROR = "error";
	@Autowired
	QuoteService quoteService;

	@ExceptionHandler(QuoteExistException.class)
	protected ResponseEntity<HandledException> handleConflictException(QuoteExistException e, HttpServletRequest request){
		String name = (String) request.getAttribute("name");
		if (!"get".equalsIgnoreCase(request.getMethod())) {
			Operation operation = getOperation(request.getMethod());
			quoteService.perform(name, operation, e);
		}
		HandledException response = HandledException.builder()
				.errorCode(1)
				.description(e.getMessage())
				.level(LEVEL_ERROR)
				.build();
		logger.error(e.getMessage(), e);		
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	private Operation getOperation(String method) {
		Operation operation = null;
		switch (method) {
		case "POST": operation = Operation.CREATE;			
			break;
		case "PUT": operation = Operation.UPDATE;			
			break;
		case "DELETE": operation = Operation.DELETE;			
			break;
		}
		return operation;
	}

	@ExceptionHandler(QuoteNotFoundException.class)
	protected ResponseEntity<HandledException> handleNotFoundException(QuoteNotFoundException e, HttpServletRequest request){
		String name = (String) request.getAttribute("name");
		if (!"get".equalsIgnoreCase(request.getMethod())) {
			Operation operation = getOperation(request.getMethod());
			quoteService.perform(name, operation, e);
		}
		HandledException response = HandledException.builder()
				.errorCode(2)
				.description(e.getMessage())
				.level(LEVEL_ERROR)
				.build();
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(NegativeArgumentException.class)
	protected ResponseEntity<HandledException> handleNegativeArgumentException(NegativeArgumentException e, HttpServletRequest request){
		String name = (String) request.getAttribute("name");
		if (!"get".equalsIgnoreCase(request.getMethod())) {
			Operation operation = getOperation(request.getMethod());
			quoteService.perform(name, operation, e);
		}
		HandledException response = HandledException.builder()
				.errorCode(3)
				.description(e.getMessage())
				.level(LEVEL_ERROR)
				.build();
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(IllegalNameException.class)
	protected ResponseEntity<HandledException> handleIllegalNameException(IllegalNameException e, HttpServletRequest request){
		String name = (String) request.getAttribute("name");
		if (!"get".equalsIgnoreCase(request.getMethod())) {
			Operation operation = getOperation(request.getMethod());
			quoteService.perform(name, operation, e);
		}
		HandledException response = HandledException.builder()
				.errorCode(4)
				.description(e.getMessage())
				.level(LEVEL_ERROR)
				.build();
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@AllArgsConstructor
	@Getter
	@Setter
	@Builder
	private static class HandledException{
		private int errorCode;
		private String description;
		private String level;
	}
}
