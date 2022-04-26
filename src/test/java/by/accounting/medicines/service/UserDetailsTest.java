package by.accounting.medicines.service;

import by.accounting.medicines.IntegrationTestBase;
import by.accounting.medicines.auth.jwt.model.UserDetailsImpl;
import by.accounting.medicines.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserDetailsTest extends IntegrationTestBase {
    @Test
    public void getUserAuthoritiesReturnAuthorities() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.getAuthorities()).isEqualTo(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
    }

    @Test
    public void isAccountNonExpiredReturnTrue() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isAccountNonExpired()).isTrue();
    }

    @Test
    public void isAccountNonLockedReturnTrue() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isAccountNonLocked()).isTrue();
    }

    @Test
    public void isCredentialsNonExpiredReturnTrue() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
    }

    @Test
    public void isEnabledReturnTrue() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isEnabled()).isTrue();
    }

    @Test
    public void gettersTest() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
    }
}
