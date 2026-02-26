package med.salus.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Patients", description = "Operations related to patient management")
public class PatientController {

    private final PatientService service;
    private final PatientMapper mapper;

    public PatientController(PatientService patientService, PatientMapper mapper) {
        this.service = patientService;
        this.mapper = mapper;
    }

    @PostMapping()
    @Operation(
            summary = "Register a new patient",
            description = "Creates a new patient in the system. The patient must provide valid personal and contact"
                    + " information.")
    public ResponseEntity<PatientResponseDTO> registerPatient(
            @Valid @RequestBody PatientInputDTO dto, UriComponentsBuilder builder) {

        Patient patient = service.savePatient(dto);

        PatientResponseDTO responseDTO = mapper.toResponseDTO(patient);

        var uri = builder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(summary = "List all patients", description = "Returns a paginated list of registered patients.")
    @GetMapping()
    public ResponseEntity<Page<PatientListDTO>> listPatient(
            @PageableDefault(
                            size = 10,
                            sort = {"name"})
                    Pageable pp) {
        var page = service.listAllPatient(pp);

        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Update patient information", description = "Updates the data of an existing patient.")
    @PutMapping
    public ResponseEntity<PatientResponseDTO> updatePatient(@RequestBody @Valid PatientUpdateDTO dto) {

        var responseDTO = service.saveUpdatePatient(dto);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            summary = "Deactivate a patient",
            description = "Performs a logical deletion (deactivation) of a patient by ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        service.deactivatePatient(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get patient details", description = "Retrieves detailed information of a patient by ID.")
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> showPatientsDetails(@PathVariable Long id) {

        var responseDTO = service.patientDetails(id);

        return ResponseEntity.ok(responseDTO);
    }
}
