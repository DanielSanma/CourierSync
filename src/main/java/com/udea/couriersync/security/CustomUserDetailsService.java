package com.udea.couriersync.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udea.couriersync.entity.User;
import com.udea.couriersync.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // --- CORRECCIÓN: Inyección por Constructor ---

    // Declaramos el campo como final
    private final UserRepository userRepository;

    /**
     * Constructor para inyectar la dependencia UserRepository.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // --- FIN DE LA CORRECCIÓN ---

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
        return UserPrincipal.create(user);
    }
}