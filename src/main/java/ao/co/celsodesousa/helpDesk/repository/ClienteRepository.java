package ao.co.celsodesousa.helpDesk.repository;

import ao.co.celsodesousa.helpDesk.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	
	public Optional<Cliente> findByCpf(String cpf);
	public Optional<Cliente> findByEmail(String email);
	
}
