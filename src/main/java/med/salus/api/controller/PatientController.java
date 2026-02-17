package med.salus.api.controller;

import jakarta.validation.Valid;
import med.salus.api.domain.entity.Patient;
import med.salus.api.dto.request.PatientInputDTO;
import med.salus.api.dto.request.PatientUpdateDTO;
import med.salus.api.dto.response.PatientListDTO;
import med.salus.api.dto.response.PatientResponseDTO;
import med.salus.api.mapper.PatientMapper;
import med.salus.api.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("patients")
public class PatientController {

    private final PatientService service;
    private final PatientMapper mapper;

    public PatientController(PatientService patientService, PatientMapper mapper) {
        this.service = patientService;
        this.mapper = mapper;
    }

    @PostMapping()
    public ResponseEntity<PatientResponseDTO> registerPatient(
            @Valid @RequestBody PatientInputDTO dto, UriComponentsBuilder builder) {

        Patient patient = service.savePhysician(dto);

        PatientResponseDTO responseDTO = mapper.toResponseDTO(patient);

        var uri =
                builder.path("/physicians/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping()
    public ResponseEntity<Page<PatientListDTO>> listPatient(
            @PageableDefault(
                            size = 10,
                            sort = {"name"})
                    Pageable pp) {
        var page = service.listAllPatient(pp);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    public ResponseEntity<PatientResponseDTO> updatePatient(@RequestBody @Valid PatientUpdateDTO dto) {

        var responseDTO = service.saveUpdatePatient(dto);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePhysician(@PathVariable Long id) {
        service.deactivatePatient(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> showPhysicianDetails(@PathVariable Long id) {

        var responseDTO = service.patientDetails(id);

        return ResponseEntity.ok(responseDTO);
    }
}
