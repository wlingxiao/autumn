package autumn.token.support;

import autumn.token.TokenUser;

import java.util.Optional;

public interface TokenManager {

    String createToken(TokenUser tokenUser);

    Optional<TokenUser> parseToken(String token);

}
