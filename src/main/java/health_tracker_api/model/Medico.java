package health_tracker_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 20)
    private String crm;

    @Column(length = 100)
    private String especialidade;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    // Um médico pode estar vinculado a vários pacientes
    @ManyToMany
    @JoinTable(
        name = "medico_paciente",
        joinColumns = @JoinColumn(name = "medico_id"),
        inverseJoinColumns = @JoinColumn(name = "paciente_id")
    )
    @Builder.Default
    private List<Paciente> pacientes = new ArrayList<>();

    // Um médico pode ter enviado vários convites
    @OneToMany(mappedBy = "criadoPorMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Convite> convitesEnviados = new ArrayList<>();
}
