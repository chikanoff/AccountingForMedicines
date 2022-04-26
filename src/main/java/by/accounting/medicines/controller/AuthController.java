package by.accounting.medicines.controller;

import by.accounting.medicines.model.dto.request.auth.SignInRequest;
import by.accounting.medicines.model.dto.response.JwtResponse;
import by.accounting.medicines.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public JwtResponse login(@RequestBody SignInRequest req) {
        return authService.login(req);
    }
}