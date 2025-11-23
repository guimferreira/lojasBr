package com.lojasbrasileiras.lojasbr.supplier.controller;

import com.lojasbrasileiras.lojasbr.supplier.dto.SupplierRegisterDTO;
import com.lojasbrasileiras.lojasbr.supplier.dto.SupplierResponseDTO;
import com.lojasbrasileiras.lojasbr.supplier.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/supplier")
@RequiredArgsConstructor
@RestController
@Tag(name = "Supplier", description = "Cadastra, lista, atualiza e deleta fornecedores")
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Cadastra fornecedor", description = "Cadastra um novo fornecedor no sistema")
    @ApiResponse(responseCode = "201", description = "Fornecedor cadastrado")
    @PostMapping("/register")
    public ResponseEntity<SupplierResponseDTO> register(@RequestBody SupplierRegisterDTO dto) {
        SupplierResponseDTO response = supplierService.createSupplier(dto);

        return ResponseEntity
                .created(URI.create("/suppliers/" + response.getId()))
                .body(response);
    }

    @ApiResponse(responseCode = "200", description = "Fornecedores listados")
    @Operation(summary = "Listar fornecedores", description = "Lista todos os fornecedores cadastrados no sistema")
    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @ApiResponse(responseCode = "200", description = "Fornecedor encontrado")
    @Operation(summary = "Pesquisa fornecedor pelo Id", description = "Encontra fornecedor cadastrado no sistema pelo Id")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @ApiResponse(responseCode = "200", description = "Fornecedor encontrado")
    @Operation(summary = "Pesquisa fornecedor pelo CNPJ", description = "Encontra fornecedor cadastrado no sistema pelo CNPJ")
    @GetMapping("/cnpj")
    public ResponseEntity<SupplierResponseDTO> getSupplierByCnpj(@RequestParam String cnpj) {
        return ResponseEntity.ok(supplierService.getSupplierByCnpj(cnpj));
    }

}
