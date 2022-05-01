package by.accounting.medicines.controller;


import by.accounting.medicines.IntegrationTestBase;
import by.accounting.medicines.model.dto.request.auth.SignInRequest;
import by.accounting.medicines.model.dto.request.user.PatchUserRequest;
import by.accounting.medicines.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String getTokenFromAuthorization() throws Exception {
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

        return Objects.requireNonNull(result.getResponse().getCookie("accessToken")).getValue();
    }

    @Test
    public void getUserInfoReturnsStatusOk() throws Exception {
        String token = getTokenFromAuthorization();

        mvc.perform(get("/api/user")
                        .cookie(new Cookie("accessToken", token))
                        .contentType("application/json"))
                        .andExpect(status().isOk());
    }

    @Test
    public void changePasswordReturnsStatusOk() throws Exception {
        String token = getTokenFromAuthorization();

        PatchUserRequest req = new PatchUserRequest();
        req.setPassword("newPassword");

        mvc.perform(patch("/api/user")
                        .cookie(new Cookie("accessToken", token))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
}
