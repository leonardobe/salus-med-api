package med.salus.api.controller;

import jakarta.validation.Valid;
import med.salus.api.domain.entity.Physician;
import med.salus.api.dto.request.PhysicianInputDTO;
import med.salus.api.dto.request.PhysicianUpdateDTO;
import med.salus.api.dto.response.PhysicianListDTO;
import med.salus.api.dto.response.PhysicianResponseDTO;
import med.salus.api.mapper.PhysicianMapper;
import med.salus.api.service.PhysicianService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("physicians")
public class PhysicianController {

    private final PhysicianService service;
    private final PhysicianMapper mapper;

    public PhysicianController(PhysicianService service, PhysicianMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping()
    public ResponseEntity<PhysicianResponseDTO> registration(
            @RequestBody @Valid PhysicianInputDTO dto, UriComponentsBuilder builder) {

        Physician physician = service.savePhysician(dto);

        PhysicianResponseDTO responseDTO = mapper.toResponseDTO(physician);

        var uri = builder.path("/physicians/{id}")
                .buildAndExpand(physician.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping()
    public ResponseEntity<Page<PhysicianListDTO>> listPhysicians(
            @PageableDefault(
                            size = 10,
                            sort = {"name"})
                    Pageable pp) {
        var page = service.listAllPhysicians(pp);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    public ResponseEntity<PhysicianResponseDTO> updatePhysician(@RequestBody @Valid PhysicianUpdateDTO dto) {

        var responseDTO = service.saveUpdatePhysician(dto);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePhysician(@PathVariable Long id) {
        service.deactivatePhysician(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicianResponseDTO> showPhysicianDetails(@PathVariable Long id) {

        var responseDTO = service.physicianDetails(id);

        return ResponseEntity.ok(responseDTO);
    }
}
