package ao.co.celsodesousa.helpDesk.domain.dto.convert;

import ao.co.celsodesousa.helpDesk.domain.Tecnico;
import ao.co.celsodesousa.helpDesk.domain.dto.TecnicoNotIdDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class TecnicoDTOConvertDomain implements Converter<TecnicoNotIdDTO, Tecnico> {

	@Override
	public Tecnico convert(MappingContext<TecnicoNotIdDTO, Tecnico> mappingContext) {

		TecnicoNotIdDTO source = mappingContext.getSource();
		Tecnico dest = new Tecnico();
		// Convert string to timestamp like in other example
		dest.addPerfil(source.getPerfils());
		dest.setCpf(source.getCpf());
		dest.setEmail(source.getEmail());
		dest.setNome(source.getNome());
		dest.setSenha(source.getSenha());
		
		
		return dest;
	}

}
