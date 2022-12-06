package ao.co.celsodesousa.helpDesk.domain.dto;

import ao.co.celsodesousa.helpDesk.domain.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoNotIdDTO {

	
	
	@NotBlank(message = "Nome e Obrigatorio")
	protected String nome;
	
	@NotBlank(message = "CPF e Obrigatorio")
	protected String cpf;
	@NotBlank(message = "Email e Obrigatorio")
	protected String email;

	@NotBlank(message = "Senha e Obrigatorio")
	protected String senha;

	protected Set<Perfil> perfils = new HashSet<Perfil>();
	
	
	

}
