package med.salus.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Patient")
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String cpf;

    private Address address;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}
