package ao.co.celsodesousa.helpDesk.domain;

import ao.co.celsodesousa.helpDesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Entity
public abstract class Pessoa implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	@Column(nullable = false,length = 150)
	protected String nome;
	@Column(nullable = false,length = 50,unique = true)
	protected String cpf;
	@Column(nullable = false,length = 255,unique = true)
	protected String email;
	@Column(nullable = false)
	protected String senha;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	protected Set<Integer> perfils = new HashSet<Integer>();
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();
	
	public Pessoa(Integer id) {
		
		this.id = id;
	}
	
	public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
	}
	
	

	public Pessoa(Integer id, String nome, String cpf, String email, String senha, Set<Integer> perfils) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.perfils = perfils;
	}
	




	public Pessoa(String nome, String cpf, String email, String senha, Set<Integer> perfils) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.perfils = perfils;
	}
	
	public Pessoa(String nome, String cpf, String email, String senha) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
	}

	public Pessoa() {
		
		addPerfil(Perfil.CLIENTE);
	}
	
	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getCpf() {
		return cpf;
	}
	public String getEmail() {
		return email;
	}
	public String getSenha() {
		return senha;
	}
	public Set<Perfil> getPerfils() {
		return perfils.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	public void addPerfil(Perfil perfil) {
		
		this.perfils.add(perfil.getCode());
	}
public void addPerfil(Set<Perfil> perfils) {
	
	
	  for(Perfil perfil : perfils) {
		  
		  addPerfil(perfil);
	  }
		
	
	}
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setPerfils(Set<Integer> perfils) {
		this.perfils = perfils;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
	
	

}
