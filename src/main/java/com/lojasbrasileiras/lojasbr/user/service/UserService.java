package com.lojasbrasileiras.lojasbr.user.service;

import com.lojasbrasileiras.lojasbr.user.dto.UserRegisterDTO;
import com.lojasbrasileiras.lojasbr.user.dto.UserResponseDTO;
import com.lojasbrasileiras.lojasbr.user.dto.UserUpdateDTO;
import com.lojasbrasileiras.lojasbr.user.mapper.UserMapper;
import com.lojasbrasileiras.lojasbr.user.model.User;
import com.lojasbrasileiras.lojasbr.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRegisterDTO dto) {
        User user = userMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        User user = getUserEntityById(id);
        return userMapper.toResponseDTO(user);
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user = getUserEntityByEmail(email);
        return userMapper.toResponseDTO(user);
    }

    public UserResponseDTO patchUser(Long id, UserUpdateDTO dto) {

        User user = getUserEntityById(id);

        userMapper.updateEntityFromUpdateDTO(dto, user);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updated = userRepository.save(user);
        return userMapper.toResponseDTO(updated);
    }

    public void deleteUser(Long id) {
        User user = getUserEntityById(id);
        userRepository.delete(user);
    }

    // MÉTODOS INTERNOS — NOTA: não expõem entidades no controller
    private User getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id));
    }

    private User getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com email: " + email));
    }

}
