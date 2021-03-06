package by.accounting.medicines.service;

import by.accounting.medicines.IntegrationTestBase;
import by.accounting.medicines.model.dto.request.user.PatchUserRequest;
import by.accounting.medicines.model.dto.response.UserResponse;
import by.accounting.medicines.model.entity.User;
import com.github.dockerjava.api.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

public class UserServiceTest extends IntegrationTestBase {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void changePasswordTest() {
        User user = createTestUser();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null));
        PatchUserRequest req = new PatchUserRequest();
        String newPassword = "newPassword";
        req.setPassword(newPassword);
        userService.changePassword(req);
        User changedUser = getUserRepository().findByUsername(user.getUsername()).get();
        assertThat(passwordEncoder.matches(newPassword, changedUser.getPassword())).isTrue();
}

    @Test
    public void getUserInfoTest() {
        User user = createTestUser();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null));
        UserResponse response = userService.getUserResponse();
        assertThat(response.getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getFullName()).isEqualTo(user.getFullName());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getRole()).isEqualTo(user.getRole().name());
    }
}
