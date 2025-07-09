package io.github.security_s_01.service;

import io.github.security_s_01.entites.User;
import io.github.security_s_01.repositories.UserRepository;
import io.github.security_s_01.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);

        return userByUsername
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found : " + username));
    }
}
