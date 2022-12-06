package ao.co.celsodesousa.helpDesk.domain;

import ao.co.celsodesousa.helpDesk.domain.enums.Prioridade;
import ao.co.celsodesousa.helpDesk.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
@Data
@NoArgsConstructor
@Entity
public class Chamado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAbertura = LocalDate.now();
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFechamento;
	@Enumerated(EnumType.ORDINAL)
	private Prioridade prioridade;
	@Column(nullable = false,length = 255)
	private String titulo;
	@Lob
	@Column(nullable = false)
	private String observacao;
	@Enumerated(EnumType.ORDINAL)
	private Status status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(foreignKey = @ForeignKey)
	private Tecnico tecnico;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(foreignKey = @ForeignKey)
	private Cliente cliente;
	
	
	
	public Chamado(Prioridade prioridade, String titulo, String observacao, Tecnico tecnico, Cliente cliente) {
		super();
		this.prioridade = prioridade;
		this.titulo = titulo;
		this.observacao = observacao;
		this.tecnico = tecnico;
		this.cliente = cliente;
		
		this.status = Status.ABERTO;
	}
	
	


	public Chamado(Integer id, LocalDate dataAbertura, LocalDate dataFechamento, Prioridade prioridade, String titulo,
			String observacao, Status status, Tecnico tecnico, Cliente cliente) {
		super();
		this.id = id;
		this.dataAbertura = dataAbertura;
		this.dataFechamento = dataFechamento;
		this.prioridade = prioridade;
		this.titulo = titulo;
		this.observacao = observacao;
		this.status = status;
		this.tecnico = tecnico;
		this.cliente = cliente;
	}
	

	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chamado other = (Chamado) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}




	
	
	
	
	

}
