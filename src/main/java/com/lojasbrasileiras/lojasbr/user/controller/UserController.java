package com.lojasbrasileiras.lojasbr.user.controller;

import com.lojasbrasileiras.lojasbr.user.dto.UserResponseDTO;
import com.lojasbrasileiras.lojasbr.user.dto.UserUpdateDTO;
import com.lojasbrasileiras.lojasbr.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
@Tag(name = "User", description = "Lista, atualiza e deleta usuários")
public class UserController {

    private final UserService userService;

    @ApiResponse(responseCode = "200", description = "Usuários listados")
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários registrados no sistema")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @Operation(summary = "Pesquisa usuário pelo Id", description = "Encontra usuário registrado no sistema pelo Id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @Operation(summary = "Pesquisa usuário pelo e-mail", description = "Encontra usuário registrado no sistema pelo e-mail")
    @GetMapping("/email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @ApiResponse(responseCode = "204", description = "Usuário atualizado")
    @Operation(summary = "Atualiza usuário", description = "Atualizado os dados de um usuário registrado no sistema")
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> patchUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDTO dto
    ) {
        return ResponseEntity.ok(userService.patchUser(id, dto));
    }

    @ApiResponse(responseCode = "204", description = "Usuário deletado")
    @Operation(summary = "Deleta usuário", description = "Deleta um usuário registrado no sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
}
