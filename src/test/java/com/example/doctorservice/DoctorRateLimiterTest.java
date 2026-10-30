package com.example.doctorservice;

import com.example.doctorservice.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration"
})
@AutoConfigureMockMvc
class DoctorRateLimiterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DoctorRepository repository;

    @Test
    void sixthSearchWithinPeriodReturns429() throws Exception {
        when(repository.findByNameContainingIgnoreCase("An")).thenReturn(List.of());

        for (int i = 0; i < 5; i++) {
            mockMvc.perform(get("/api/v1/doctors/search").param("name", "An"))
                    .andExpect(status().isOk());
        }

        mockMvc.perform(get("/api/v1/doctors/search").param("name", "An"))
                .andExpect(status().isTooManyRequests());
        verify(repository, times(5)).findByNameContainingIgnoreCase("An");
    }
}
