package ao.co.celsodesousa.helpDesk.domain.dto.convert;

import ao.co.celsodesousa.helpDesk.domain.dto.TecnicoDTO;
import ao.co.celsodesousa.helpDesk.domain.dto.TecnicoNotIdDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class TecnicoConvertDTO implements Converter<TecnicoNotIdDTO, TecnicoDTO>{

	@Override
	public TecnicoDTO convert(MappingContext<TecnicoNotIdDTO, TecnicoDTO> context) {
		// TODO Auto-generated method stub
		return null;
	}

}
