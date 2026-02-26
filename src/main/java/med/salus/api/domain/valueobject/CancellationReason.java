package med.salus.api.domain.valueobject;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Reason for cancellation", example = "PATIENT_GAVE_UP")
public enum CancellationReason {
    PATIENT_GAVE_UP,
    PHYSICIAN_CANCELED,
    OTHER
}
