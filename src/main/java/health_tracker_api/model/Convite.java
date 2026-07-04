package health_tracker_api.model;


import health_tracker_api.model.enums.CanalEnvio;
import health_tracker_api.model.enums.StatusConvite;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "convite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Convite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por_medico_id", nullable = false)
    private Medico criadoPorMedico;

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal_envio", nullable = false, length = 20)
    private CanalEnvio canalEnvio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusConvite status = StatusConvite.pendente;

    @Column(name = "expira_em", nullable = false)
    private LocalDateTime expiraEm;

    @Column(name = "aceito_em")
    private LocalDateTime aceitoEm;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
