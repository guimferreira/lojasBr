package com.lojasbrasileiras.lojasbr.security.controller;

import com.lojasbrasileiras.lojasbr.security.dto.TokenResponseDTO;
import com.lojasbrasileiras.lojasbr.security.jwt.JwtProvider;
import com.lojasbrasileiras.lojasbr.user.dto.*;
import com.lojasbrasileiras.lojasbr.user.mapper.UserMapper;
import com.lojasbrasileiras.lojasbr.user.model.User;
import com.lojasbrasileiras.lojasbr.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    // ---------------------------------------------------------
    // REGISTER
    // ---------------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO dto) {
        User created = userService.createUser(dto);

        UserResponseDTO response = userMapper.toResponseDTO(created);

        return ResponseEntity
                .created(null) // opcional: URI.create("/users/" + created.getId())
                .body(response);
    }

    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody UserLoginDTO dto) {
        // autentica (lança excepción se inválido)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        // Se autenticado, gerar token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtProvider.generateToken(userDetails.getUsername());

        TokenResponseDTO response = new TokenResponseDTO(token, "Bearer", jwtProvider.getExpirationInSeconds());
        return ResponseEntity.ok(response);
    }

    // Optional: refresh token endpoint (implementar se precisar)
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refreshToken(@RequestHeader("Authorization") String bearer) {
        if (bearer == null || !bearer.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        String token = bearer.substring(7);

        if (!jwtProvider.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }

        String username = jwtProvider.getUsernameFromToken(token);
        String newToken = jwtProvider.generateToken(username);

        return ResponseEntity.ok(new TokenResponseDTO(newToken, "Bearer", jwtProvider.getExpirationInSeconds()));
    }
}

