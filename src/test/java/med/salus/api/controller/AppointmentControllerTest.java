package med.salus.api.controller;

import med.salus.api.domain.valueobject.Specialty;
import med.salus.api.dto.request.AppointmentDTO;
import med.salus.api.dto.response.AppointmentResponseDTO;
import med.salus.api.service.AppointmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<AppointmentDTO> jacksonAppointmentRequest;

    @Autowired
    private JacksonTester<AppointmentResponseDTO> jacksonAppointmentResponse;

    @MockitoBean
    private AppointmentService service;

    @Nested
    class cancelAppointment {
        @Test
        @DisplayName("Should return bad request when appointment does not exist")
        @WithMockUser
        void should() throws Exception {
            var response = mockMvc.perform(post("/appointments/{id}/cancel", 2L))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class scheduleAppointments {

        @Test
        @DisplayName("Should return code 400 when information is invalid")
        @WithMockUser
        void shouldReturn4OOWhenInfoInvalid() throws Exception {
            var response = mockMvc.perform(post("/appointments")).andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @DisplayName("Should return code 201 when information is valid.")
        @WithMockUser
        void shouldReturn2O1WhenInfoValid() throws Exception {

            var date = LocalDateTime.now().plusHours(1);

            when(service.scheduleAppointment(any())).thenReturn(new AppointmentResponseDTO(null, 2L, 5L, date));

            var response = mockMvc.perform(post("/appointments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jacksonAppointmentRequest
                                    .write(new AppointmentDTO(2L, 5L, date, Specialty.RADIOLOGY))
                                    .getJson()))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

            var result = jacksonAppointmentResponse
                    .write(new AppointmentResponseDTO(null, 2L, 5L, date))
                    .getJson();

            assertThat(response.getContentAsString()).isEqualTo(result);
        }
    }
}
