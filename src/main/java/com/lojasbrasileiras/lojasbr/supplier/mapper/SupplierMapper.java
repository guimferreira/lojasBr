package com.lojasbrasileiras.lojasbr.supplier.mapper;

import com.lojasbrasileiras.lojasbr.supplier.dto.SupplierRegisterDTO;
import com.lojasbrasileiras.lojasbr.supplier.dto.SupplierResponseDTO;
import com.lojasbrasileiras.lojasbr.supplier.model.Supplier;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toEntity(SupplierRegisterDTO dto);

    SupplierResponseDTO toResponseDTO(Supplier supplier);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDTO(SupplierRegisterDTO dto, @MappingTarget Supplier supplier);
}
