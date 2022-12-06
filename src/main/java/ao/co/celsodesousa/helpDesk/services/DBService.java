package ao.co.celsodesousa.helpDesk.services;

import ao.co.celsodesousa.helpDesk.domain.Chamado;
import ao.co.celsodesousa.helpDesk.domain.Cliente;
import ao.co.celsodesousa.helpDesk.domain.Tecnico;
import ao.co.celsodesousa.helpDesk.domain.enums.Perfil;
import ao.co.celsodesousa.helpDesk.domain.enums.Prioridade;
import ao.co.celsodesousa.helpDesk.repository.ChamadoRepository;
import ao.co.celsodesousa.helpDesk.repository.ClienteRepository;
import ao.co.celsodesousa.helpDesk.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public void instanciaDB() {
		
		
		Tecnico tecnico = new Tecnico("Celso de Sousa", "342233", "celso@gmail.com", bCryptPasswordEncoder.encode("1234"));
		tecnico.addPerfil(Perfil.TECNICO);

		Tecnico tecnico1 = new Tecnico("Celso de Sousa", "55545", "teste@gmail.com", bCryptPasswordEncoder.encode("1111"));
		tecnico.addPerfil(Perfil.TECNICO);
		
		
		Cliente cliente = new Cliente("Mauro Francisco", "563542342", "mauro@gmail.com", bCryptPasswordEncoder.encode("0000"));
		
		Prioridade prioridade = Prioridade.BAIXA;





		tecnico = tecnicoRepository.save(tecnico);
		tecnico1 = tecnicoRepository.save(tecnico1);
		cliente = clienteRepository.save(cliente);

		System.out.println(tecnico);
		System.out.println(cliente);

		Chamado chamado = new Chamado(prioridade, "Primeiro", "analise de requisitos", tecnico, cliente);
		chamadoRepository.save(chamado);
		
		
	}

}
