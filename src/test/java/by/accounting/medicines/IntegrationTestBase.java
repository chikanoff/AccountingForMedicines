package by.accounting.medicines;

import by.accounting.medicines.model.entity.User;
import by.accounting.medicines.model.entity.UserRole;
import by.accounting.medicines.repository.UserRepository;
import com.github.dockerjava.api.model.TaskStatus;
import liquibase.pro.packaged.U;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Getter
public class IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User createTestUser() {
        return userRepository.save(
                User.builder()
                        .fullName("testFullName")
                        .username("test")
                        .email("testEmail@gmail.com")
                        .password(encoder.encode("password"))
                        .role(UserRole.ROLE_USER)
                        .build());
    }


    public User createAdmin() {
        return userRepository.save(
                User.builder()
                        .fullName("admin")
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(encoder.encode("password"))
                        .role(UserRole.ROLE_ADMIN)
                        .build());
    }


    @BeforeEach
    public void resetDb() {
        userRepository.deleteAll();
    }
}
