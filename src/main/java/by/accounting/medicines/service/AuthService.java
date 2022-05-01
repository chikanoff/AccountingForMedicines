package by.accounting.medicines.service;

import by.accounting.medicines.auth.jwt.JwtService;
import by.accounting.medicines.model.dto.request.auth.SignInRequest;
import by.accounting.medicines.model.dto.response.JwtResponse;
import by.accounting.medicines.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserService userService;

    /**
     * Returns jwt result of authentication
     *
     * @param req Request with username and password fields
     * @return JwtResponse
     */
    public ResponseEntity<JwtResponse> login(SignInRequest req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        jwtService.addTokenToCookie(headers, jwt);
        return ResponseEntity.ok().headers(headers).body(new JwtResponse(
                userDetails.getUsername(),
                userDetails.getAuthorities()
                        .stream()
                        .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN")))
        );
    }

    public ResponseEntity<Boolean> check(String accessToken) {
        return ResponseEntity.ok(jwtService.validateToken(accessToken));
    }

    public ResponseEntity<JwtResponse> currentUser() {
        User user = userService.getUserFromJwt();
        if(user == null) {
            return ResponseEntity.ok(new JwtResponse());
        }
        return ResponseEntity.ok(new JwtResponse(user.getUsername(), user.getRole().name().equals("ROLE_ADMIN")));
    }

    public ResponseEntity<String> logout(String accessToken) {
        jwtService.invalidateToken(accessToken);
        return ResponseEntity.ok("success");
    }
}
