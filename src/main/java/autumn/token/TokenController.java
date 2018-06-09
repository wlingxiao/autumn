package autumn.token;

import autumn.Result;
import autumn.token.support.JwtTokenProperties;
import autumn.token.support.TokenManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(tags = "token")
@RestController
@RequestMapping("/token")
@Slf4j
public class TokenController {

    private TokenManager tokenManager;

    private JwtTokenProperties jwtTokenProperties;

    private AuthenticationManager authenticationManager;

    public TokenController(TokenManager tokenManager, JwtTokenProperties jwtTokenProperties, AuthenticationManager authenticationManager) {
        this.tokenManager = tokenManager;
        this.jwtTokenProperties = jwtTokenProperties;
        this.authenticationManager = authenticationManager;
    }

    @ApiOperation(value = "创建token", response = TokenResult.class)
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public Result createAuthToken(@Valid @RequestBody TokenParam tokenParam, HttpServletResponse response) {
/*        log.debug("获取 token {}", authRequest.toString());
        val authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        val userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        val token = jwtTokenHelper.generateToken(userDetails);
        response.addCookie(new Cookie("Authentication", token));*/

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(tokenParam.getUsername(), tokenParam.getPassword()));
            val tokenUser = new TokenUser();
            tokenUser.setUsername(tokenParam.getUsername());
            String token = tokenManager.createToken(tokenUser);
            response.addCookie(new Cookie(getTokenName(), token));
            return new TokenResult(200, token);
        } catch (AuthenticationException e) {
            log.info("用户名或密码错误", e);
            return new Result(400, "用户名或密码错误");
        }

    }

    private String getTokenName() {
        return jwtTokenProperties.getName();
    }
}
