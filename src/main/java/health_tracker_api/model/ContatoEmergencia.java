package health_tracker_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "contato_emergencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoEmergencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 50)
    private String parentesco;

    @Column(nullable = false, length = 20)
    private String telefone;
}
