package ao.co.celsodesousa.helpDesk.domain.dto.convert;

import ao.co.celsodesousa.helpDesk.domain.Cliente;
import ao.co.celsodesousa.helpDesk.domain.dto.ClienteNotIdDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ClienteDTOConvertDomain implements Converter<ClienteNotIdDTO, Cliente> {

	@Override
	public Cliente convert(MappingContext<ClienteNotIdDTO, Cliente> mappingContext) {

		ClienteNotIdDTO source = mappingContext.getSource();
		Cliente dest = new Cliente();
		// Convert string to timestamp like in other example
	//	dest.addPerfil(source.getPerfils());
		dest.setCpf(source.getCpf());
		dest.setEmail(source.getEmail());
		dest.setNome(source.getNome());
		dest.setSenha(source.getSenha());
		
		
		
		return dest;
	}

}
