package by.accounting.medicines.jwt;

import by.accounting.medicines.IntegrationTestBase;
import by.accounting.medicines.auth.jwt.JwtService;
import by.accounting.medicines.model.dto.request.auth.SignInRequest;
import by.accounting.medicines.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JwtAuthTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void userGetCorrectToken() throws Exception {
        User user = createTestUser();
        SignInRequest req = new SignInRequest();
        req.setUsername(user.getUsername());
        req.setPassword("password");

        MvcResult result = mvc.perform(
                        post("/api/auth/signin")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk()).andReturn();

        String token = Objects.requireNonNull(result.getResponse().getCookie("accessToken")).getValue();

        assertThat(jwtService.getUsernameFrom(token)).isEqualTo(req.getUsername());
    }
}
