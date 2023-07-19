package krutyporokh.FastParcel.DeliveryService.security;

import io.jsonwebtoken.*;
import krutyporokh.FastParcel.DeliveryService.services.EmployeeDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;
    private final EmployeeDetailsService employeeDetailsService;

    public JwtTokenProvider(EmployeeDetailsService employeeDetailsService) {
        this.employeeDetailsService = employeeDetailsService;
    }

    public String generateToken(Authentication authentication) {
        EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(employeeDetails.getUsername())
                .claim("email", employeeDetails.getUsername()) //getUsername is this case returns an email
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
    public Authentication getAuthentication(String token) {
        EmployeeDetails employeeDetails = (EmployeeDetails) employeeDetailsService.loadUserByUsername(getUsernameFromJWT(token));
        return new UsernamePasswordAuthenticationToken(employeeDetails, "", employeeDetails.getAuthorities());
    }

}
