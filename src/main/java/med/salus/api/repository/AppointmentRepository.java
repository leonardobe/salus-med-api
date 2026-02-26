package med.salus.api.repository;

import med.salus.api.domain.entity.Appointment;
import med.salus.api.domain.valueobject.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPhysicianIdAndAppointmentDate(Long physicianId, LocalDateTime appointmentDate);

    boolean existsByPatientIdAndAppointmentDateAndStatus(
            Long patientId, LocalDateTime appointmentDate, AppointmentStatus status);
}
