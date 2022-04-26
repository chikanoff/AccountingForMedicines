package by.accounting.medicines.controller;

import by.accounting.medicines.IntegrationTestBase;
import by.accounting.medicines.model.dto.request.auth.SignInRequest;
import by.accounting.medicines.model.dto.request.user.CreateUserRequest;
import by.accounting.medicines.model.dto.request.user.PatchUserRequest;
import by.accounting.medicines.model.dto.request.user.UpdateUserRequest;
import by.accounting.medicines.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAdminControllerTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String getTokenFromAuthorization() throws Exception {
        User admin = createAdmin();
        SignInRequest req = new SignInRequest();
        req.setUsername(admin.getUsername());
        req.setPassword("password");
        MvcResult result = mvc.perform(
                        post("/api/auth/signin")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk()).andReturn();

        return result.getResponse().getContentAsString()
                .replace("{\"token\":\"", "")
                .replace("\"}", "");
    }

    @Test
    public void createUserReturnsOk() throws Exception {
        CreateUserRequest req = new CreateUserRequest();
        req.setFullName("user1");
        req.setUsername("user1");
        req.setEmail("user1@gmail.com");
        req.setPassword("password");

        String adminToken = getTokenFromAuthorization();
        mvc.perform(post("/api/admin/users/")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserReturnsOk() throws Exception {
        User user = createTestUser();

        UpdateUserRequest req = new UpdateUserRequest();
        req.setFullName("newFullName");
        req.setUsername("user1");
        req.setEmail("user1@gmail.com");

        final String adminToken = getTokenFromAuthorization();
        mvc.perform(put("/api/admin/users/" + user.getId())
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    public void changePasswordReturnsOk() throws Exception {
        User user = createTestUser();

        PatchUserRequest req = new PatchUserRequest();
        req.setPassword("newPassword");

        String adminToken = getTokenFromAuthorization();
        mvc.perform(patch("/api/admin/users/" + user.getId())
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    public void getOneReturnsOk() throws Exception {
        User user = createTestUser();

        String adminToken = getTokenFromAuthorization();
        mvc.perform(get("/api/admin/users/" + user.getId())
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllReturnsOk() throws Exception {
        createTestUser();
        final String params = "?page=0&size=1";

        String adminToken = getTokenFromAuthorization();
        mvc.perform(get("/api/admin/users" + params)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTodoItemTest() throws Exception {
        User user = createTestUser();

        String adminToken = getTokenFromAuthorization();
        mvc.perform(delete("/api/admin/users/" + user.getId())
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true"))
                .andExpect(status().isOk());
    }

    @Test
    public void createUserWithExistEmail() throws Exception {
        User user = createTestUser();
        CreateUserRequest req = new CreateUserRequest();
        req.setFullName("user1");
        req.setUsername("user1");
        req.setEmail(user.getEmail());
        req.setPassword("password");

        String adminToken = getTokenFromAuthorization();
        mvc.perform(post("/api/admin/users/")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }

    @Test
    public void getOneReturnsNotFound() throws Exception {
        final long notExistId = 0L;
        String adminToken = getTokenFromAuthorization();
        mvc.perform(get("/api/admin/users/" + notExistId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true"))
                .andExpect(status().isNotFound());
    }

}
