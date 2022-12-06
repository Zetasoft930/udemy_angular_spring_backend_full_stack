package ao.co.celsodesousa.helpDesk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@NoArgsConstructor
public class Cliente extends Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "cliente")
	@JsonIgnore
	private List<Chamado> chamado = new ArrayList<>();

	public Cliente(String nome, String cpf, String email, String senha, List<Chamado> chamados) {
		super(nome, cpf, email, senha);
		this.chamado = chamados;
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha, List<Chamado> chamados) {
		super(id, nome, cpf, email, senha);
		this.chamado = chamados;
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);

	}

	public Cliente(String nome, String cpf, String email, String senha) {
		super(nome, cpf, email, senha);

	}

	public Cliente(Integer id) {
		super(id);
	}

}
