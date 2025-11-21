package com.lojasbrasileiras.lojasbr.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
    private String tokenType; // ex: "Bearer"
    private Long expiresIn; // segundos (opcional)
}

