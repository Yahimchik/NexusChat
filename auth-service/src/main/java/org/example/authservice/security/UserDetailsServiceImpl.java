package org.example.authservice.security;

import lombok.RequiredArgsConstructor;
import org.example.authservice.model.User;
import org.example.authservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findUserById(UUID.fromString(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return CustomSecurityUser.fromUser(user);
    }
}
