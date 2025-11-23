package com.lojasbrasileiras.lojasbr.supplier.service;

import com.lojasbrasileiras.lojasbr.supplier.dto.SupplierRegisterDTO;
import com.lojasbrasileiras.lojasbr.supplier.dto.SupplierResponseDTO;
import com.lojasbrasileiras.lojasbr.supplier.mapper.SupplierMapper;
import com.lojasbrasileiras.lojasbr.supplier.model.Supplier;
import com.lojasbrasileiras.lojasbr.supplier.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierResponseDTO createSupplier(SupplierRegisterDTO dto) {
        Supplier supplier = supplierMapper.toEntity(dto);

        Supplier saved = supplierRepository.save(supplier);

        return supplierMapper.toResponseDTO(saved);
    }

    public List<SupplierResponseDTO> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(supplierMapper::toResponseDTO)
                .toList();
    }

    public SupplierResponseDTO getSupplierById(Long id) {
        Supplier supplier = getSupplierEntityById(id);
        return supplierMapper.toResponseDTO(supplier);
    }

    public SupplierResponseDTO getSupplierByCnpj(String cnpj) {
        Supplier supplier = getSupplierEntityByCnpj(cnpj);
        return supplierMapper.toResponseDTO(supplier);
    }

    public SupplierResponseDTO patchSupplier(Long id, SupplierRegisterDTO dto) {

        Supplier supplier = getSupplierEntityById(id);

        supplierMapper.updateEntityFromUpdateDTO(dto, supplier);

        Supplier updated = supplierRepository.save(supplier);
        return supplierMapper.toResponseDTO(updated);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = getSupplierEntityById(id);
        supplierRepository.delete(supplier);
    }

    // MÉTODOS INTERNOS — NOTA: não expõem entidades no controller
    private Supplier getSupplierEntityById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado: " + id));
    }

    private Supplier getSupplierEntityByCnpj(String cnpj) {
        return supplierRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com CNPJ: " + cnpj));
    }
}
