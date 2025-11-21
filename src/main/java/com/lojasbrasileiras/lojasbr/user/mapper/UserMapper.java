package com.lojasbrasileiras.lojasbr.user.mapper;

import com.lojasbrasileiras.lojasbr.user.dto.UserRegisterDTO;
import com.lojasbrasileiras.lojasbr.user.dto.UserUpdateDTO;
import com.lojasbrasileiras.lojasbr.user.dto.UserResponseDTO;
import com.lojasbrasileiras.lojasbr.user.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegisterDTO dto);

    UserResponseDTO toResponseDTO(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDTO(UserUpdateDTO dto, @MappingTarget User user);
}

