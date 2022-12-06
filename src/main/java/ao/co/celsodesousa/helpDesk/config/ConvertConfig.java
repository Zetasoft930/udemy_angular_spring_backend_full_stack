package ao.co.celsodesousa.helpDesk.config;

import ao.co.celsodesousa.helpDesk.domain.Chamado;
import ao.co.celsodesousa.helpDesk.domain.Cliente;
import ao.co.celsodesousa.helpDesk.domain.Tecnico;
import ao.co.celsodesousa.helpDesk.domain.dto.ChamadoDTO;
import ao.co.celsodesousa.helpDesk.domain.dto.ChamadoNotIdDTO;
import ao.co.celsodesousa.helpDesk.domain.dto.ClienteNotIdDTO;
import ao.co.celsodesousa.helpDesk.domain.dto.TecnicoNotIdDTO;
import ao.co.celsodesousa.helpDesk.domain.dto.convert.ChamadoConvertDTO;
import ao.co.celsodesousa.helpDesk.domain.dto.convert.ChamadoDTOConvertDomain;
import ao.co.celsodesousa.helpDesk.domain.dto.convert.ClienteDTOConvertDomain;
import ao.co.celsodesousa.helpDesk.domain.dto.convert.TecnicoDTOConvertDomain;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConvertConfig {
	
	@Bean
	public ModelMapper modelMapperConfig() {
		ModelMapper model = new ModelMapper();
		
		model.addConverter(new TecnicoDTOConvertDomain(), TecnicoNotIdDTO.class, Tecnico.class);
		model.addConverter(new ClienteDTOConvertDomain(), ClienteNotIdDTO.class, Cliente.class);
		model.addConverter(new ChamadoConvertDTO(), Chamado.class, ChamadoDTO.class);
		model.addConverter(new ChamadoDTOConvertDomain(), ChamadoNotIdDTO.class, Chamado.class);
		
		return model;
	}


}
