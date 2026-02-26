package med.salus.api.repository;

import med.salus.api.domain.entity.Appointment;
import med.salus.api.domain.entity.Patient;
import med.salus.api.domain.entity.Physician;
import med.salus.api.domain.valueobject.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PhysiciansRepositoryTest {

    @Autowired
    private PhysiciansRepository physiciansRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private void scheduleAppointment(Physician physician, Patient patient, LocalDateTime appointmentDate) {

        var appointment = new Appointment(physician, patient, appointmentDate);

        testEntityManager.persist(appointment);
    }

    private Physician registerPhysician(
            String name, String email, String phone, String medicalRegistration, Specialty specialty) {

        var physician = new Physician();
        physician.setName(name);
        physician.setEmail(email);
        physician.setPhone(phone);
        physician.setMedicalRegistration(medicalRegistration);
        physician.setSpecialty(specialty);
        physician.setActive(true);

        testEntityManager.persist(physician);

        return physician;
    }

    private Patient registerPatient(String name, String email, String phone, String cpf) {

        var patient = new Patient();
        patient.setName(name);
        patient.setEmail(email);
        patient.setPhone(phone);
        patient.setCpf(cpf);
        patient.setActive(true);

        testEntityManager.persist(patient);

        return patient;
    }

    @Nested
    @DisplayName("findAvailableBySpecialtyOrderByBusy")
    class FindAvailableBySpecialty {
        @Test
        @DisplayName("Should return empty when the only physician is unavailable on that date")
        void shouldReturnEmptyWhenNoPhysicianIsAvailable() {
            // Arrange or given
            var appointmentDate = LocalDate.now()
                    .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .atTime(10, 0);

            var physician = registerPhysician("João", "joao@email.com", "51912345678", "123456", Specialty.DERMATOLOGY);

            // Act or when
            var result = physiciansRepository.findAvailableBySpecialtyOrderByBusy(
                    Specialty.DERMATOLOGY, appointmentDate, PageRequest.of(0, 1));

            // Assert or then
            assertThat(result.getContent()).contains(physician);
        }

        @Test
        @DisplayName("Should return physician when available")
        void shouldReturnPhysicianWhenAvailable() {
            var appointmentDate = LocalDate.now()
                    .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .atTime(10, 0);

            var physician =
                    registerPhysician("Pedro", "pedro@email.com", "51912343078", "126056", Specialty.DERMATOLOGY);
            var patient = registerPatient("Maria", "maria@email.com", "51123456789", "12345678901");

            scheduleAppointment(physician, patient, appointmentDate);

            var result = physiciansRepository.findAvailableBySpecialtyOrderByBusy(
                    Specialty.DERMATOLOGY, appointmentDate, PageRequest.of(0, 1));

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Should ignore physicians from other specialties")
        void shouldIgnoreOtherSpecialties() {
            // Arrange
            var appointmentDate = LocalDate.now()
                    .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .atTime(10, 0);

            var dermatologist =
                    registerPhysician("Dr João", "derm@email.com", "51912345673", "123456", Specialty.DERMATOLOGY);

            registerPhysician("Dr Carlos", "cardio@email.com", "51912405620", "134568", Specialty.CARDIOLOGY);

            // Act
            var result = physiciansRepository.findAvailableBySpecialtyOrderByBusy(
                    Specialty.DERMATOLOGY, appointmentDate, PageRequest.of(0, 1));

            // Assert
            assertThat(result.getContent()).contains(dermatologist);

            assertThat(result.getContent()).hasSize(1);
        }
    }
}
