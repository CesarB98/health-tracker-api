package health_tracker_api.repositories;

import health_tracker_api.model.ContatoEmergencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContatoEmergenciaRepository extends JpaRepository<ContatoEmergencia, UUID> {

    // Lista todos os contatos de emergência de um paciente
    List<ContatoEmergencia> findByPacienteId(UUID pacienteId);
}
