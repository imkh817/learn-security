package example.security_s_03.managers;

import example.security_s_03.providers.ApiKeyProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AuthenticationProvider provider = new ApiKeyProvider(key);

        if(provider.supports(authentication.getClass())){
            return provider.authenticate(authentication);
        }

        return authentication;
    }
}
