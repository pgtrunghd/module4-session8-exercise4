package com.example.doctorservice;

import com.example.doctorservice.controller.DoctorController;
import com.example.doctorservice.dto.DoctorResponse;
import com.example.doctorservice.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DoctorService doctorService;

    @Test
    void getAllReturnsDoctorList() throws Exception {
        when(doctorService.findAll()).thenReturn(List.of(
                new DoctorResponse(1L, "Tran Van B", "Noi khoa"),
                new DoctorResponse(2L, "Le Thi C", "Nhi khoa")));

        mockMvc.perform(get("/api/v1/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Tran Van B"))
                .andExpect(jsonPath("$[0].specialization").value("Noi khoa"))
                .andExpect(jsonPath("$[0].email").doesNotExist());
    }
}
