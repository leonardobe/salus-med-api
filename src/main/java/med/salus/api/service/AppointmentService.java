package med.salus.api.service;

import med.salus.api.domain.entity.Appointment;
import med.salus.api.domain.entity.Physician;
import med.salus.api.domain.validation.AppointmentSchedulingValidator;
import med.salus.api.domain.valueobject.AppointmentStatus;
import med.salus.api.dto.request.AppointmentDTO;
import med.salus.api.dto.request.CancelAppointmentDTO;
import med.salus.api.dto.response.AppointmentResponseDTO;
import med.salus.api.dto.response.CancelAppointmentResponseDTO;
import med.salus.api.exception.BusinessException;
import med.salus.api.exception.ConflictException;
import med.salus.api.exception.ResourceNotFoundException;
import med.salus.api.mapper.AppointmentMapper;
import med.salus.api.mapper.CancelMapper;
import med.salus.api.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;
    private final PatientService patientService;
    private final PhysicianService physicianService;
    private final List<AppointmentSchedulingValidator> validators;
    private final AppointmentMapper mapper;
    private final CancelMapper cancelMapper;

    public AppointmentService(
            AppointmentRepository repository,
            PatientService patientService,
            PhysicianService physicianService,
            List<AppointmentSchedulingValidator> validators,
            AppointmentMapper mapper,
            CancelMapper cancelMapper) {
        this.repository = repository;
        this.patientService = patientService;
        this.physicianService = physicianService;
        this.validators = validators;
        this.mapper = mapper;
        this.cancelMapper = cancelMapper;
    }

    public AppointmentResponseDTO scheduleAppointment(AppointmentDTO dto) {

        var patient = patientService.findPatientById(dto.patientId());

        validators.forEach(v -> v.validate(dto));

        var physician = resolvePhysician(dto);

        var appointment = new Appointment(physician, patient, dto.appointmentDate());

        repository.save(appointment);

        return mapper.toResponseDTO(appointment);
    }

    private Physician resolvePhysician(AppointmentDTO dto) {

        if (dto.physicianId() != null) {
            return physicianService.findAvailableById(dto.physicianId(), dto.appointmentDate());
        }

        if (dto.specialty() != null) {
            return physicianService.findAvailableBySpecialty(dto.specialty(), dto.appointmentDate());
        }

        throw new BusinessException("Specialty is required when physicianId is not provided");
    }

    public CancelAppointmentResponseDTO cancel(Long id, CancelAppointmentDTO dto) {

        var appointment =
                repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        validateCancellation(appointment, dto);

        appointment.setStatus(AppointmentStatus.CANCELED);
        appointment.setCancellationReason(dto.reason());
        appointment.setCancellationDate(LocalDateTime.now());

        repository.save(appointment);

        return cancelMapper.toResponseDTO(appointment);
    }

    private void validateCancellation(Appointment appointment, CancelAppointmentDTO dto) {

        if (appointment.getStatus() == AppointmentStatus.CANCELED) {
            throw new ConflictException("Appointment is already canceled");
        }

        var hours = Duration.between(LocalDateTime.now(), appointment.getAppointmentDate())
                .toHours();

        if (hours < 24) {
            throw new ConflictException("Appointments must be canceled at least 24 hours in advance");
        }
    }
}
