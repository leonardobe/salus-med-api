package med.salus.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.salus.api.domain.valueobject.Specialty;

import java.time.LocalDateTime;

public record AppointmentDTO(
        Long physicianId,
        @NotNull Long patientId,

        @Schema(example = "2026-03-10T14:00:00") @NotNull @Future
        LocalDateTime appointmentDate,

        Specialty specialty) {}
