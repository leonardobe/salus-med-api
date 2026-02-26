package med.salus.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.salus.api.dto.request.AppointmentDTO;
import med.salus.api.dto.request.CancelAppointmentDTO;
import med.salus.api.dto.response.AppointmentResponseDTO;
import med.salus.api.dto.response.CancelAppointmentResponseDTO;
import med.salus.api.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Appointments", description = "Operations related to appointments")
@RestController
@RequestMapping("appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @Operation(summary = "Schedule a new medical appointment")
    @PostMapping()
    public ResponseEntity<AppointmentResponseDTO> scheduleAppointment(@RequestBody @Valid AppointmentDTO dto) {
        var appointment = service.scheduleAppointment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @Operation(summary = "Cancel an appointment")
    @PostMapping("{id}/cancel")
    public ResponseEntity<CancelAppointmentResponseDTO> cancelAppointment(
            @PathVariable Long id, @RequestBody @Valid CancelAppointmentDTO dto) {
        var appointmentCanceled = service.cancel(id, dto);

        return ResponseEntity.ok(appointmentCanceled);
    }
}
