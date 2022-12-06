package ao.co.celsodesousa.helpDesk.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError extends StandardError  {

	
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<FieldMessage>();
	
	
	public void add(String fieldName,String message) {
		
		this.errors.add(new FieldMessage(fieldName, message)); 
	}


	public ValidationError(LocalDateTime now, int value, String string, String message, String requestURI) {
		
		super(now, value, string, message, requestURI);
	}
	

}
