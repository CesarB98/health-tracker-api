package health_tracker_api.repositories;

import health_tracker_api.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {

    // Busca médico por email — usado no login
    Optional<Medico> findByEmail(String email);

    // Verifica se já existe médico com esse email — usado no cadastro
    boolean existsByEmail(String email);

    // Verifica se já existe médico com esse CRM
    boolean existsByCrm(String crm);
}
