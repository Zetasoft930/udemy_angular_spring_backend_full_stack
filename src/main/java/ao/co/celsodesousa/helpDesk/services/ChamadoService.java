package ao.co.celsodesousa.helpDesk.services;

import ao.co.celsodesousa.helpDesk.domain.Chamado;
import ao.co.celsodesousa.helpDesk.domain.enums.Status;
import ao.co.celsodesousa.helpDesk.exceptions.DataIntegrityViolationException;
import ao.co.celsodesousa.helpDesk.exceptions.ObjectNotFoundException;
import ao.co.celsodesousa.helpDesk.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ChamadoRepository repository;

	public Chamado findById(Integer id) {

		Optional<Chamado> optional = repository.findById(id);

		return optional
				.orElseThrow(() -> new ObjectNotFoundException("Nao foi econtrado nenhum Chamado com esse id =" + id));

	}

	
	public List<Chamado> findAll() {

		return this.repository.findAll();
	}

	public List<Chamado> findByStatus(Status status) {

		return this.repository.findByStatus(status);
	}

	@Transactional
	public Chamado save(Chamado model) {

		
		return this.repository.save(model);
	}

	@Transactional

	public Chamado update(Chamado model) {

		Chamado modelAntigo = findById(model.getId());

		if (modelAntigo != null) {
			

			return this.repository.save(model);
		}
		return null;
	}
	
	@Transactional
	public Chamado delete(Integer id) {
		
		
		Chamado modelAntigo = findById(id);
		
		if(modelAntigo != null && modelAntigo.getStatus()== Status.ABERTO) {
			
			
			repository.delete(modelAntigo);
			
			return modelAntigo;
			
			
			
		}
		
		
		throw new DataIntegrityViolationException("Chamado possui ordem de servico");
		
		
	}
}
