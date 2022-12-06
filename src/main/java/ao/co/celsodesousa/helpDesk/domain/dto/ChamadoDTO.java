package ao.co.celsodesousa.helpDesk.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class ChamadoDTO extends ChamadoNotIdDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	public ChamadoDTO(Integer prioridade, String titulo, String observacao, Integer tecnico, Integer cliente) {
		super(prioridade, titulo, observacao, tecnico, cliente);
		// TODO Auto-generated constructor stub
		
		
	}

	public ChamadoDTO(LocalDate dataFechamento, Integer prioridade, String titulo, String observacao, Integer status,
			Integer tecnico, Integer cliente) {
		super(dataFechamento, prioridade, titulo, observacao, status, tecnico, cliente);
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	

}
