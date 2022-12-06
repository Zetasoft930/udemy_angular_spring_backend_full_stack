package ao.co.celsodesousa.helpDesk.domain.dto.convert;

import ao.co.celsodesousa.helpDesk.domain.Chamado;
import ao.co.celsodesousa.helpDesk.domain.Cliente;
import ao.co.celsodesousa.helpDesk.domain.Tecnico;
import ao.co.celsodesousa.helpDesk.domain.dto.ChamadoNotIdDTO;
import ao.co.celsodesousa.helpDesk.domain.enums.Prioridade;
import ao.co.celsodesousa.helpDesk.domain.enums.Status;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ChamadoDTOConvertDomain implements Converter<ChamadoNotIdDTO, Chamado> {

	@Override
	public Chamado convert(MappingContext<ChamadoNotIdDTO, Chamado> mappingContext) {

		ChamadoNotIdDTO source = mappingContext.getSource();
		Chamado dest = new Chamado();
		
		dest.setCliente(new Cliente(source.getCliente().getId()));
		dest.setTecnico(new Tecnico(source.getTecnico().getId()));
		dest.setDataAbertura(source.getDataAbertura());
		dest.setDataFechamento(source.getDataFechamento());
		dest.setTitulo(source.getTitulo());
		dest.setObservacao(source.getObservacao());
		dest.setPrioridade(Prioridade.toEnum(source.getPrioridade()));
		dest.setStatus(Status.toEnum(source.getStatus()));
		
		return dest;
	}

}
