package med.salus.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import med.salus.api.domain.valueobject.AppointmentStatus;
import med.salus.api.domain.valueobject.CancellationReason;

import java.time.LocalDateTime;

@Entity(name = "Appointment")
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id")
    private Physician physician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "cancellation_reason")
    @Enumerated(EnumType.STRING)
    private CancellationReason cancellationReason;

    @Column(name = "cancellation_date")
    private LocalDateTime cancellationDate;

    public Appointment(Physician physician, Patient patient, LocalDateTime appointmentDate) {
        this.physician = physician;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.status = AppointmentStatus.SCHEDULED;
    }
}
