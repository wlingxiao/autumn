package autumn.token.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * iss(Issuser)：代表这个JWT的签发主体；
 * sub(Subject)：代表这个JWT的主体，即它的所有人；
 * aud(Audience)：代表这个JWT的接收对象，例如可以拆分为 mobile 和 web 使用不同的 token
 * exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
 * nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
 * iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
 * jti(JWT ID)：是JWT的唯一标识。
 * <q>http://www.cnblogs.com/davidwang456/p/6478968.html</q>
 */
@Deprecated
public class JwtTokenHelper {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * token 签发时间
     */
    @SuppressWarnings("unused")
    private Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    /**
     * token 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        val claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        val claims = new HashMap<String, Object>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        val createdDate = new Date();
        val expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @SuppressWarnings("unused")
    private String refreshToken(String token) {
        val claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(new Date());
        return doRefreshToken(claims);
    }

    private String doRefreshToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        val user = (JwtTokenUser) userDetails;
        val username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
}
