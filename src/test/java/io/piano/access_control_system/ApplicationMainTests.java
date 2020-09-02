package io.piano.access_control_system;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationMainTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void room4EntranceTrueKey10000shouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/check?roomId=4&entrance=true&keyId=10000"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void room4EntranceFalseKey10000shouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/check?roomId=4&entrance=false&keyId=10000"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void room4EntranceFalseKey10000shouldReturn403() throws Exception {
        this.mockMvc.perform(get("/check?roomId=4&entrance=false&keyId=10000"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void room3EntranceTrueKey10000shouldReturn403() throws Exception {
        this.mockMvc.perform(get("/check?roomId=3&entrance=True&keyId=10000"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void room8EntranceTrueKey10000shouldReturn500() throws Exception {
        this.mockMvc.perform(get("/check?roomId=8&entrance=True&keyId=10000"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void room4EntranceTrueKey10004shouldReturn500() throws Exception {
        this.mockMvc.perform(get("/check?roomId=4&entrance=True&keyId=10004"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

}
