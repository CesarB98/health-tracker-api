package health_tracker_api.model;

import health_tracker_api.model.enums.OrigemRegistro;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "registro_peso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroPeso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // UUID de quem registrou (médico ou paciente) — sem FK tipada
    // pois pode referenciar duas tabelas diferentes
    @Column(name = "registrado_por", nullable = false)
    private UUID registradoPor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrigemRegistro origem;

    @Column(name = "peso_kg", nullable = false, precision = 5, scale = 2)
    private BigDecimal pesoKg;

    @Column(name = "imc_calculado", precision = 5, scale = 2)
    private BigDecimal imcCalculado;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @CreationTimestamp
    @Column(name = "registrado_em", nullable = false, updatable = false)
    private LocalDateTime registradoEm;
}