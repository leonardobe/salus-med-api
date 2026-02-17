package med.salus.api.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import med.salus.api.domain.entity.Patient;
import med.salus.api.dto.request.PatientInputDTO;
import med.salus.api.dto.request.PatientUpdateDTO;
import med.salus.api.dto.response.PatientListDTO;
import med.salus.api.dto.response.PatientResponseDTO;
import med.salus.api.mapper.PatientMapper;
import med.salus.api.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;

    public PatientService(PatientRepository repository, PatientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public Patient savePhysician(PatientInputDTO dto) {

        Patient patient = mapper.toEntityFromCreateDTO(dto);

        return repository.save(patient);
    }

    public Page<PatientListDTO> listAllPatient(Pageable pp) {
        return repository.findAllByActiveTrue(pp).map(PatientMapper::toDTO);
    }

    @Transactional
    public PatientResponseDTO saveUpdatePatient(PatientUpdateDTO dto) {

        var physician = repository.getReferenceById(dto.id());

        mapper.updateEntityFromDTO(dto, physician);

        return mapper.toResponseDTO(physician);
    }

    @Transactional
    public void deactivatePatient(Long id) {
        var patient = repository.getReferenceById(id);

        if (!patient.isActive()) {
            return;
        }

        patient.setActive(false);
    }

    public PatientResponseDTO patientDetails(Long id) {
        Patient patient =
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Physician not found"));

        mapper.toResponseDTO(patient);

        return mapper.toResponseDTO(patient);
    }
}
