package quote.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IllegalNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IllegalNameException() {
		super("name is empty");
	}

}
