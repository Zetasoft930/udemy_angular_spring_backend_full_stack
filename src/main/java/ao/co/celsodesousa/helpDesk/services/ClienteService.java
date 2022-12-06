package ao.co.celsodesousa.helpDesk.services;

import ao.co.celsodesousa.helpDesk.domain.Cliente;
import ao.co.celsodesousa.helpDesk.exceptions.DataIntegrityViolationException;
import ao.co.celsodesousa.helpDesk.exceptions.ObjectNotFoundException;
import ao.co.celsodesousa.helpDesk.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ClienteRepository repository;

	public Cliente findById(Integer id) {

		Optional<Cliente> optional = repository.findById(id);

		return optional
				.orElseThrow(() -> new ObjectNotFoundException("Nao foi econtrado nenhum Cliente com esse id =" + id));

	}

	private void validate(Cliente modelo) {

		Optional<Cliente> optional = this.repository.findByCpf(modelo.getCpf());

		if (optional.isPresent() && modelo.getId() != optional.get().getId()) {

			throw new DataIntegrityViolationException("CPF Ja existe no sistema");

		} else {

			optional = this.repository.findByEmail(modelo.getEmail());

			if (optional.isPresent() && modelo.getId() != optional.get().getId()) {

				throw new DataIntegrityViolationException("Email Ja existe no sistema");

			}
		}

	}

	public List<Cliente> findAll() {

		return this.repository.findAll();
	}

	@Transactional
	public Cliente save(Cliente model) {

		validate(model);
		return this.repository.save(model);
	}

	@Transactional

	public Cliente update(Cliente model) {

		Cliente modelAntigo = findById(model.getId());

		if (modelAntigo != null) {
			validate(model);

			return this.repository.save(model);
		}
		return null;
	}
	
	@Transactional
	public Cliente delete(Integer id) {
		
		
		Cliente modelAntigo = findById(id);
		
		if(modelAntigo != null && modelAntigo.getChamado().size() == 0) {
			
			
			repository.delete(modelAntigo);
			
			return modelAntigo;
			
			
			
		}
		
		
		throw new DataIntegrityViolationException("Cliente possui ordem de servico");
		
		
	}
}
