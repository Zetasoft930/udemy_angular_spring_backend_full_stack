package ao.co.celsodesousa.helpDesk.repository;

import ao.co.celsodesousa.helpDesk.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

	Optional<Pessoa> findByEmail(String email);

}
