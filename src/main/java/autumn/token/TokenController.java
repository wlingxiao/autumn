package autumn.token;

import autumn.token.config.JwtTokenHelper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@RestController
@RequestMapping("/token")
@Slf4j
public class TokenController {

    private JwtTokenHelper jwtTokenHelper;

    private UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public TokenController(JwtTokenHelper jwtTokenHelper, UserDetailsService userDetailsService,
                           AuthenticationManager authenticationManager) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * 登录，获取 token
     */
    @PostMapping(consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> createAuthToken(AuthRequest authRequest, HttpServletResponse response) throws AuthenticationException {
        log.debug("获取 token {}", authRequest.toString());
        val authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        val userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        val token = jwtTokenHelper.generateToken(userDetails);
        response.addCookie(new Cookie("Authentication", token));
        return ResponseEntity.ok(new AuthResponse(token));
    }

}
