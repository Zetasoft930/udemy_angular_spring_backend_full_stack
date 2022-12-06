package ao.co.celsodesousa.helpDesk.domain.dto;

import ao.co.celsodesousa.helpDesk.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ChamadoNotIdDTO {
	
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAbertura = LocalDate.now();
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFechamento;
	
	private Integer prioridade;
@NotBlank
@NotEmpty
	private String titulo;

	@NotBlank
	@NotEmpty
	private String observacao;
	
	private Integer status;
	

	private Tecnico tecnico;

	private Cliente cliente;
	

	@NoArgsConstructor
	@Getter
	public static class Cliente{
		
		public Cliente(Integer id, String nome) {
			// TODO Auto-generated constructor stub
			this.id = id;
			this.nome = nome;
		}
		private Integer id;
		private String nome;
	}

	@NoArgsConstructor
	@Getter
	public static class Tecnico{
		
		public Tecnico(Integer id, String nome) {
			// TODO Auto-generated constructor stub
			this.id = id;
			this.nome = nome;
		}
		private Integer id;
		private String nome;
	}

	public ChamadoNotIdDTO(Integer prioridade, String titulo, String observacao, Integer tecnico, Integer cliente) {
		super();
		this.prioridade = prioridade;
		this.titulo = titulo;
		this.observacao = observacao;
		this.tecnico = new Tecnico(tecnico, "");
		this.cliente = new Cliente(cliente, "");
		
		
		this.status = Status.ABERTO.getCode();
	}

	public ChamadoNotIdDTO(LocalDate dataFechamento, Integer prioridade, String titulo, String observacao,
			Integer status, Integer tecnico, Integer cliente) {
		super();
		this.dataFechamento = dataFechamento;
		this.prioridade = prioridade;
		this.titulo = titulo;
		this.observacao = observacao;
		this.status = status;
		this.tecnico = new Tecnico(tecnico, "");
		this.cliente = new Cliente(cliente, "");
	}

	public ChamadoNotIdDTO() {
		super();
		this.status = Status.ABERTO.getCode();
	}
	
	
	
	

}
