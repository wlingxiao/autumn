package autumn.token.support;

import autumn.token.TokenUser;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenManager implements TokenManager {

    private JwtTokenProperties jwtTokenProperties;

    public JwtTokenManager(JwtTokenProperties jwtTokenProperties) {
        this.jwtTokenProperties = jwtTokenProperties;
    }

    @Override
    public String createToken(TokenUser tokenUser) {
        val claims = new HashMap<String, Object>();
        claims.put("id", tokenUser.getId());
        claims.put("username", tokenUser.getUsername());
        return doCreateToken(claims, tokenUser.getUsername());
    }

    private String doCreateToken(Map<String, Object> claims, String subject) {
        val createdDate = new Date();
        val expirationDate = new Date(System.currentTimeMillis() + getExpire() * 10000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, getSecret())
                .compact();
    }

    @Override
    public Optional<TokenUser> parseToken(String token) {
        try {
            val claims = getAllClaimsFromToken(token);
            val userId = claims.get("id", Long.class);
            val username = claims.get("username", String.class);
            val tokenUser = new TokenUser();
            tokenUser.setId(userId);
            tokenUser.setUsername(username);
            return Optional.of(tokenUser);
        } catch (ClaimJwtException e) {
            log.warn("parse token failed", e.getMessage());
            return Optional.empty();
        }
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    private Long getExpire() {
        return jwtTokenProperties.getExpire();
    }

    private String getSecret() {
        return jwtTokenProperties.getSecret();
    }
}
