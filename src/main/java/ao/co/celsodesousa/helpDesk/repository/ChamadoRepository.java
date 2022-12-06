package ao.co.celsodesousa.helpDesk.repository;

import ao.co.celsodesousa.helpDesk.domain.Chamado;
import ao.co.celsodesousa.helpDesk.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

    @Query("SELECT u FROM Chamado u WHERE u.status=?1")
    public List<Chamado> findByStatus(Status status);

}
