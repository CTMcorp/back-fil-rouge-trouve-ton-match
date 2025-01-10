package fr.initiativedeuxsevres.ttm.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // injecte le secret key
    @Value("${security.jwt.token.secret-key}")
    private String jwtSecret;

    @Value("${security.jwt.token.expiration-milliseconds}")
    private long jwtExpiration;

    // méthode pour générer un token pour un user authentifié
    public String generateToken(Authentication authentication) {
        // récup le username
        String username = authentication.getName();

        Date currentDate = new Date();
        // date d'expiration du token
        Date expireDate = new Date(currentDate.getTime() + jwtExpiration);

        // construction du token
        return Jwts.builder()
                .subject(username) // définit le sujet du token
                .issuedAt(new Date()) // date d'émission du token
                .expiration(expireDate) // date d'expiration
                .signWith(key()) // signe le token avec la secret key
                .compact(); // compacte le token en une chaine
    }

    // méthode pour générer la secret key pour signer le token
    public SecretKey key() {
        // décode la secret key et génère une clé HMAC
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // méthode pour récup le username à partir du token
    public String getUsername(String token) {
        return Jwts.parser() // crée un parser
                .verifyWith(key())
                .build() // construit le parser
                .parseSignedClaims(token)// parse les claims
                .getPayload() // recup le payload du token
                .getSubject(); // recup le username
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }
}
