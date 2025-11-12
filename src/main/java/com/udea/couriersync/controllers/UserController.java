package com.udea.couriersync.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.udea.couriersync.dto.SignUpRequest;
import com.udea.couriersync.dto.UserDTO;
import com.udea.couriersync.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    // --- CORRECCIÓN: Inyección por Constructor ---

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor para inyectar todas las dependencias (UserService y PasswordEncoder).
     */
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // --- FIN DE LA CORRECCIÓN ---

    @PostMapping("/new")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody SignUpRequest signUpRequest) {
        UserDTO userDTO = signUpRequest.toUserDTO();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDTO created = userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<UserDTO> list() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto) {
        UserDTO updated = userService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}