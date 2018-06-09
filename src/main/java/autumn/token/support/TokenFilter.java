package autumn.token.support;

import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private JwtTokenProperties jwtTokenProperties;

    private TokenManager tokenManager;

    public TokenFilter(JwtTokenProperties jwtTokenProperties, TokenManager tokenManager) {
        this.jwtTokenProperties = jwtTokenProperties;
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        val cookies = request.getCookies();
        if (cookies != null) {
            val tokenValue = Arrays.stream(cookies)
                    .filter(c -> Objects.equals(c.getName(), getTokenHeader()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(request.getHeader(getTokenHeader()));
            tokenManager.parseToken(tokenValue)
                    .ifPresent(tokenUser -> {
                        val authentication = new UsernamePasswordAuthenticationToken(tokenUser, null, tokenUser.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenHeader() {
        return jwtTokenProperties.getName();
    }
}
