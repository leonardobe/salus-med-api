package med.salus.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import med.salus.api.dto.Specialty;

@Entity(name = "Physician")
@Table(name = "physicians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Physician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String medicalRegistration;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    private Address address;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}
