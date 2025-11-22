package com.lojasbrasileiras.lojasbr.security.controller;

import com.lojasbrasileiras.lojasbr.security.dto.TokenResponseDTO;
import com.lojasbrasileiras.lojasbr.security.jwt.JwtProvider;
import com.lojasbrasileiras.lojasbr.user.dto.*;
import com.lojasbrasileiras.lojasbr.user.mapper.UserMapper;
import com.lojasbrasileiras.lojasbr.user.model.User;
import com.lojasbrasileiras.lojasbr.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
@Tag(name = "Auth", description = "Autenticação e registro")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    @Operation(summary = "Registra usuário", description = "Cria um novo usuário no sistema")
    @ApiResponse(responseCode = "201", description = "Usuário criado")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO dto) {
        User created = userService.createUser(dto);

        UserResponseDTO response = userMapper.toResponseDTO(created);

        return ResponseEntity
                .created(null)
                .body(response);
    }

    @Operation(summary = "Login", description = "Autentica e retorna JWT")
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

}

