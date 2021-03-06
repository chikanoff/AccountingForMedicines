package by.accounting.medicines.auth.jwt;

import by.accounting.medicines.auth.jwt.model.UserDetailsImpl;
import by.accounting.medicines.auth.util.DateUtil;
import by.accounting.medicines.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtKeyProvider jwtKeyProvider;
    private final DateUtil dateUtil;

    public String generateToken(UserDetails userDetails) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plus(jwtProperties.getExpiration());

        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setExpiration(dateUtil.toDate(expiryDate))
                .signWith(SignatureAlgorithm.RS256, jwtKeyProvider.getPrivateKey())
                .claim("username", userDetails.getUsername())
                .claim("role", authorities)
                .compact();
    }

    public boolean validateToken(String jwt) {
        if(jwt == null) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(jwtKeyProvider.getPublicKey()).parseClaimsJws(jwt);
            return true;
        } catch (JwtException e) {
            log.warn("Invalid JWT!", e);
        }
        return false;
    }

    public void addTokenToCookie(HttpHeaders headers, String token) {
        headers.add(HttpHeaders.SET_COOKIE, ResponseCookie.from(jwtProperties.getAccessToken(), token)
                .maxAge(jwtProperties.getExpiration())
                .httpOnly(true)
                .path("/")
                .sameSite("None")
                .secure(true).build().toString());
    }

    public String getUsernameFrom(String jwt) {
        return (String) getClaims(jwt).get("username");
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .setSigningKey(jwtKeyProvider.getPublicKey())
                .parseClaimsJws(jwt)
                .getBody();
    }

    public void invalidateToken(HttpHeaders headers, String accessToken) {
        headers.add(HttpHeaders.SET_COOKIE, ResponseCookie.from(jwtProperties.getAccessToken(), null)
                .maxAge(0)
                .httpOnly(true)
                .path("/")
                .sameSite("None")
                .secure(true).build().toString());
    }
}
