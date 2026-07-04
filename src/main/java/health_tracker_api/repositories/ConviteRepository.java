package health_tracker_api.repositories;

import health_tracker_api.model.Convite;
import health_tracker_api.model.enums.StatusConvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConviteRepository extends JpaRepository<Convite, UUID> {

    // Busca convite pelo token — usado na validação e ativação de conta
    Optional<Convite> findByToken(String token);

    // Verifica se paciente já tem convite pendente
    boolean existsByPacienteIdAndStatus(UUID pacienteId, StatusConvite status);
}
