package com.lojasbrasileiras.lojasbr.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class SupplierRegisterDTO {
    private Long id;
    private String razaoSocial;
    private String cnpj;
}
