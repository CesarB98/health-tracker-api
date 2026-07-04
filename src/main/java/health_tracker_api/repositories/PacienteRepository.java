package health_tracker_api.repositories;

import health_tracker_api.model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, UUID> {

    // Busca paciente por email — usado no login do paciente
    Optional<Paciente> findByEmail(String email);

    // Verifica duplicidade de CPF — usado no cadastro
    boolean existsByCpf(String cpf);

    // Verifica duplicidade de email — usado no cadastro
    boolean existsByEmail(String email);

    // Lista paginada de pacientes vinculados a um médico, filtrando por ativo
    // e opcionalmente por nome (busca parcial, case-insensitive)
    @Query("""
        SELECT p FROM Paciente p
        JOIN p.medicos m
        WHERE m.id = :medicoId
        AND p.ativo = :ativo
        AND (:busca IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :busca, '%')))
        """)
    Page<Paciente> findByMedicoIdAndAtivo(
        @Param("medicoId") UUID medicoId,
        @Param("ativo") Boolean ativo,
        @Param("busca") String busca,
        Pageable pageable
    );

    // Verifica se um paciente está vinculado a um médico específico
    // Usado para validar permissão de acesso
    @Query("""
        SELECT COUNT(p) > 0 FROM Paciente p
        JOIN p.medicos m
        WHERE p.id = :pacienteId
        AND m.id = :medicoId
        """)
    boolean existsVinculoMedicoPaciente(
        @Param("pacienteId") UUID pacienteId,
        @Param("medicoId") UUID medicoId
    );
}
