package ao.co.celsodesousa.helpDesk.controller.exception;

import ao.co.celsodesousa.helpDesk.exceptions.DataIntegrityViolationException;
import ao.co.celsodesousa.helpDesk.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
																HttpServletRequest request){
		StandardError error = new StandardError(LocalDateTime.now(), 
				HttpStatus.NOT_FOUND.value(),
				"Object Not Found",
				ex.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> violationException(DataIntegrityViolationException ex,
																HttpServletRequest request){
		StandardError error = new StandardError(LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(),
				"Dados Invalido",
				ex.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException ex,
																HttpServletRequest request){
		ValidationError error = new ValidationError(LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(),
				"Erro de validacao dos campos",
				ex.getMessage(),
				request.getRequestURI());
		
		for(FieldError fieldError : ex.getBindingResult().getFieldErrors())
		{
			error.add(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
}
