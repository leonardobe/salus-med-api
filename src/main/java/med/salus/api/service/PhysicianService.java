package med.salus.api.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import med.salus.api.domain.entity.Physician;
import med.salus.api.dto.request.PhysicianInputDTO;
import med.salus.api.dto.request.PhysicianUpdateDTO;
import med.salus.api.dto.response.PhysicianListDTO;
import med.salus.api.dto.response.PhysicianResponseDTO;
import med.salus.api.mapper.PhysicianMapper;
import med.salus.api.repository.PhysiciansRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PhysicianService {

    private final PhysiciansRepository repository;
    private final PhysicianMapper mapper;

    public PhysicianService(PhysiciansRepository repository, PhysicianMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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
}
