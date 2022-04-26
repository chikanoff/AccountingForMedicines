package by.accounting.medicines.controller;

import by.accounting.medicines.model.dto.request.user.PatchUserRequest;
import by.accounting.medicines.model.dto.response.UserResponse;
import by.accounting.medicines.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@Tag(name = "Users configuration API")
@SecurityRequirement(name = "security")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserResponse getUserInfo() {
        return userService.getUserResponse();
    }

    @PatchMapping
    public void changePassword(@Valid @RequestBody PatchUserRequest req) {
        userService.changePassword(req);
    }
}
