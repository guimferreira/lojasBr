package com.lojasbrasileiras.lojasbr.user.controller;

import com.lojasbrasileiras.lojasbr.user.dto.UserRegisterDTO;
import com.lojasbrasileiras.lojasbr.user.dto.UserUpdateDTO;
import com.lojasbrasileiras.lojasbr.user.model.User;
import com.lojasbrasileiras.lojasbr.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRegisterDTO dto) {
        User createdUser = userService.createUser(dto);

        return ResponseEntity
                .created(URI.create("/users/" + createdUser.getId()))
                .body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String value) {
        User user = userService.getUserByEmail(value);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDTO dto
    ) {
        User updatedUser = userService.patchUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
}
