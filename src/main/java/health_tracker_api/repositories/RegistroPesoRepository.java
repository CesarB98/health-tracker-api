package health_tracker_api.repositories;

import health_tracker_api.model.RegistroPeso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistroPesoRepository extends JpaRepository<RegistroPeso, UUID> {

    // Lista paginada de registros de um paciente, ordenada por data decrescente
    // com filtro opcional de período
    @Query("""
        SELECT r FROM RegistroPeso r
        WHERE r.paciente.id = :pacienteId
        AND (:dataInicio IS NULL OR r.registradoEm >= :dataInicio)
        AND (:dataFim IS NULL OR r.registradoEm <= :dataFim)
        ORDER BY r.registradoEm DESC
        """)
    Page<RegistroPeso> findByPacienteId(
        @Param("pacienteId") UUID pacienteId,
        @Param("dataInicio") LocalDateTime dataInicio,
        @Param("dataFim") LocalDateTime dataFim,
        Pageable pageable
    );

    // Busca o registro mais recente de um paciente — usado para exibir
    // o último peso na listagem de pacientes da médica
    @Query("""
        SELECT r FROM RegistroPeso r
        WHERE r.paciente.id = :pacienteId
        ORDER BY r.registradoEm DESC
        LIMIT 1
        """)
    Optional<RegistroPeso> findUltimoRegistro(@Param("pacienteId") UUID pacienteId);
}
