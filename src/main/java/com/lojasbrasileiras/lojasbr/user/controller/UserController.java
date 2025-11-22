package com.lojasbrasileiras.lojasbr.user.controller;

import com.lojasbrasileiras.lojasbr.user.dto.UserUpdateDTO;
import com.lojasbrasileiras.lojasbr.user.model.User;
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
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @Operation(summary = "Pesquisa usuário pelo Id", description = "Encontra usuário registrado no sistema pelo Id")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @Operation(summary = "Pesquisa usuário pelo e-mail", description = "Encontra usuário registrado no sistema pelo e-mail")
    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String value) {
        User user = userService.getUserByEmail(value);
        return ResponseEntity.ok(user);
    }

    @ApiResponse(responseCode = "204", description = "Usuário atualizado")
    @Operation(summary = "Atualiza usuário", description = "Atualizado os dados de um usuário registrado no sistema")
    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDTO dto
    ) {
        User updatedUser = userService.patchUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @ApiResponse(responseCode = "204", description = "Usuário deletado")
    @Operation(summary = "Deleta usuário", description = "Deleta um usuário registrado no sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
}
