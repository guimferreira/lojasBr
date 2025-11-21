package com.lojasbrasileiras.lojasbr.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class UserLoginDTO {

    private String email;

    private String password;
}
