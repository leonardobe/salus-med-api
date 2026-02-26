package med.salus.api.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import med.salus.api.domain.entity.Physician;
import med.salus.api.domain.valueobject.Specialty;
import med.salus.api.dto.request.PhysicianInputDTO;
import med.salus.api.dto.request.PhysicianUpdateDTO;
import med.salus.api.dto.response.PhysicianListDTO;
import med.salus.api.dto.response.PhysicianResponseDTO;
import med.salus.api.exception.BusinessException;
import med.salus.api.mapper.PhysicianMapper;
import med.salus.api.repository.AppointmentRepository;
import med.salus.api.repository.PhysiciansRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PhysicianService {

    private final PhysiciansRepository repository;
    private final PhysicianMapper mapper;
    private final AppointmentRepository appointmentRepository;

    public PhysicianService(
            PhysiciansRepository repository, PhysicianMapper mapper, AppointmentRepository appointmentRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public Physician savePhysician(PhysicianInputDTO dto) {

        Physician physician = mapper.toEntityFromCreateDTO(dto);

        return repository.save(physician);
    }

    @Transactional
    public PhysicianResponseDTO saveUpdatePhysician(PhysicianUpdateDTO dto) {

        var physician = repository.getReferenceById(dto.id());

        mapper.updateEntityFromDTO(dto, physician);

        return mapper.toResponseDTO(physician);
    }

    public Page<PhysicianListDTO> listAllPhysicians(Pageable pp) {
        return repository.findAllByActiveTrue(pp).map(PhysicianMapper::toDTO);
    }

    @Transactional
    public void deactivatePhysician(Long id) {
        var physician = repository.getReferenceById(id);

        if (!physician.isActive()) {
            return;
        }

        physician.setActive(false);
    }

    public PhysicianResponseDTO physicianDetails(Long id) {
        Physician physician =
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Physician not found"));

        mapper.toResponseDTO(physician);

        return mapper.toResponseDTO(physician);
    }

    public Physician findAvailableById(Long id, LocalDateTime date) {

        var physician = repository
                .findById(id)
                .orElseThrow(() -> new BusinessException("Physician with id " + id + " not found"));

        if (!isAvailable(physician, date)) {
            throw new BusinessException("Physician not available at this date");
        }

        return physician;
    }

    public Physician findAvailableBySpecialty(Specialty specialty, LocalDateTime date) {

        var page = repository.findAvailableBySpecialtyOrderByBusy(specialty, date, PageRequest.of(0, 1));

        if (page.isEmpty()) {
            throw new BusinessException("No physicians available for specialty " + specialty + " at this date");
        }

        return page.getContent().getFirst();
    }

    public boolean isAvailable(Physician physician, LocalDateTime date) {

        boolean hasConflict = appointmentRepository.existsByPhysicianIdAndAppointmentDate(physician.getId(), date);

        return !hasConflict;
    }

    public Boolean isActivePhysician(Long id) {
        return repository.findByIdAndActiveTrue(id);
    }

    public boolean hasRecord(Long id) {
        return repository.existsById(id);
    }
}
