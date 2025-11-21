package com.lojasbrasileiras.lojasbr.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class UserRegisterDTO {

    private Long id;
    private String cpf;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;
}
