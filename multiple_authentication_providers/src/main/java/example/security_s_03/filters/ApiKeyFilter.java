package example.security_s_03.filters;

import example.security_s_03.authentications.ApiKeyAuthentication;
import example.security_s_03.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomAuthenticationManager manager = new CustomAuthenticationManager(key);

        String requestKey = request.getHeader("x-api-key");

        if("null".equals(requestKey) || requestKey == null){
            filterChain.doFilter(request,response);
        }

        ApiKeyAuthentication apiKeyAuthentication = new ApiKeyAuthentication(requestKey);



        try{
            Authentication authenticate = manager.authenticate(apiKeyAuthentication);
            if(authenticate.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                filterChain.doFilter(request,response);
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }catch (AuthenticationException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
