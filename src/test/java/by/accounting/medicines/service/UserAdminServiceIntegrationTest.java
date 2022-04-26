package by.accounting.medicines.service;

import by.accounting.medicines.IntegrationTestBase;
import by.accounting.medicines.model.dto.request.user.CreateUserRequest;
import by.accounting.medicines.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserAdminServiceIntegrationTest extends IntegrationTestBase {
    @Autowired
    private UserAdminService userAdminService;

    @Test
    public void createUserTest() {
        CreateUserRequest req = new CreateUserRequest();
        req.setFullName("user1");
        req.setUsername("user1");
        req.setEmail("user1@gmail.com");
        req.setPassword("password");

        userAdminService.createUser(req);

        List<User> users = getUserRepository().findAll();
        User user = users.get(users.size() - 1);
        assertThat(user.getUsername()).isEqualTo(req.getUsername());
        assertThat(user.getEmail()).isEqualTo(req.getEmail());
    }

    @Test
    public void checkEmailExistTest() {
        User user = createTestUser();
        CreateUserRequest req = new CreateUserRequest();
        req.setFullName(user.getFullName());
        req.setUsername("username");
        req.setEmail(user.getEmail());
        req.setPassword("password");

        assertThatThrownBy(() -> userAdminService.createUser(req)).hasMessage("User with this email already exist");
    }

    @Test
    public void checkUsernameExistTest() {
        User user = createTestUser();
        CreateUserRequest req = new CreateUserRequest();
        req.setFullName(user.getFullName());
        req.setUsername(user.getUsername());
        req.setEmail("email");
        req.setPassword("password");

        assertThatThrownBy(() -> userAdminService.createUser(req)).hasMessage("User with this username already exist");
    }
}
