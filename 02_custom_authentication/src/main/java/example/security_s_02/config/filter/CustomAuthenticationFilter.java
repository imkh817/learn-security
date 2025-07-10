package example.security_s_02.config.filter;


import example.security_s_02.config.authentication.CustomAuthentication;
import example.security_s_02.config.managers.CustomAuthenticationManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. create an authentication object which is not yet authenticated
        // 2. delegate the authentication object to the manager
        // 3. get back the authentication from the manager
        // 4. if the object is authenticated then send request to the next filter in the chain

        String key = String.valueOf(request.getHeader("key"));
        CustomAuthentication customAuthentication = new CustomAuthentication(false, key);

        Authentication authenticate = customAuthenticationManager.authenticate(customAuthentication);

        if(authenticate.isAuthenticated()){
            log.info("success !");
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            filterChain.doFilter(request,response); // only when authentication worked
        }
    }
}
