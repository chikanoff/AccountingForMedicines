package by.accounting.medicines.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class UserResponse {
    private Long id;

    private String fullName;

    private String username;

    private String email;

    private String password;

    private String role;
}
