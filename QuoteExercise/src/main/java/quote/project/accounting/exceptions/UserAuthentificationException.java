package quote.project.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserAuthentificationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
