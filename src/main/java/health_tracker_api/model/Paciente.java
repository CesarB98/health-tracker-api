package health_tracker_api.model;

import health_tracker_api.model.enums.StatusConta;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 20)
    private String sexo;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "senha_hash", length = 255)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_conta", nullable = false, length = 20)
    @Builder.Default
    private StatusConta statusConta = StatusConta.convidado;

    @Column(name = "altura_cm", precision = 5, scale = 2)
    private BigDecimal alturaCm;

    @Column(name = "peso_meta_kg", precision = 5, scale = 2)
    private BigDecimal pesoMetaKg;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    // Médicos vinculados a este paciente
    @ManyToMany(mappedBy = "pacientes")
    @Builder.Default
    private List<Medico> medicos = new ArrayList<>();

    // Convites recebidos por este paciente
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Convite> convites = new ArrayList<>();

    // Histórico de registros de peso
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RegistroPeso> registrosPeso = new ArrayList<>();

    // Contatos de emergência
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ContatoEmergencia> contatosEmergencia = new ArrayList<>();
}