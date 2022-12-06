package ao.co.celsodesousa.helpDesk.domain.dto.convert;

import ao.co.celsodesousa.helpDesk.domain.Chamado;
import ao.co.celsodesousa.helpDesk.domain.dto.ChamadoDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;


public class ChamadoConvertDTO implements Converter<Chamado, ChamadoDTO>{

	@Override
	public ChamadoDTO convert(MappingContext<Chamado, ChamadoDTO> mappingContext) {
		
		Chamado source = mappingContext.getSource();
		ChamadoDTO dest = new ChamadoDTO();
		
		ChamadoDTO.Tecnico tdto =	new ChamadoDTO.Tecnico(
												source.getTecnico().getId(),
												source.getTecnico().getNome());
		
		ChamadoDTO.Cliente cdto = new ChamadoDTO.Cliente(
												source.getCliente().getId(),
												source.getCliente().getNome());



		dest.setCliente(cdto);
		dest.setTecnico(tdto);

		dest.setDataAbertura(source.getDataAbertura());
		dest.setDataFechamento(source.getDataFechamento());
		dest.setObservacao(source.getObservacao());
		dest.setPrioridade(source.getPrioridade().getCode());
		dest.setStatus(source.getStatus().getCode());
		dest.setTitulo(source.getTitulo());
		dest.setId(source.getId());
		
		
		
		return dest;
	}

}
