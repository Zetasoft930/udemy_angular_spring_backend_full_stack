package ao.co.celsodesousa.helpDesk.repository;

import ao.co.celsodesousa.helpDesk.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
	
	public Optional<Tecnico> findByCpf(String cpf);
	@Query("SELECT u FROM Tecnico u WHERE u.cpf =?1 AND u.id not in(?2)")
	public Optional<Tecnico> findByCpf(String cpf,Integer id);
	public Optional<Tecnico> findByEmail(String email);
	@Query("SELECT u FROM Tecnico u WHERE u.email =?1 AND u.id not in(?2)")
	public Optional<Tecnico> findByEmail(String email,Integer id);

}
