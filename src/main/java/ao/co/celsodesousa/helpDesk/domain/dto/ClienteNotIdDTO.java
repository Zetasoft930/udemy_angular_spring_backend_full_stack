package ao.co.celsodesousa.helpDesk.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteNotIdDTO {

	
	
	@NotBlank(message = "Nome e Obrigatorio")
	protected String nome;
	
	@NotBlank(message = "CPF e Obrigatorio")
	protected String cpf;
	@NotBlank(message = "Email e Obrigatorio")
	protected String email;
	@Min(value = 8,message = "CPF deve ter no minimo 4 Caracter")
	@NotBlank(message = "Senha e Obrigatorio")
	protected String senha;

	//protected Set<Perfil> perfils = new HashSet<Perfil>();
	
	
	

}
