package by.accounting.medicines.service;

import by.accounting.medicines.exception.ItemNotFoundException;
import by.accounting.medicines.mapper.SupplierMapper;
import by.accounting.medicines.mapper.UnitMapper;
import by.accounting.medicines.model.dto.entity.SupplierDto;
import by.accounting.medicines.model.dto.entity.UnitDto;
import by.accounting.medicines.model.dto.response.SupplierResponse;
import by.accounting.medicines.model.dto.response.UnitResponse;
import by.accounting.medicines.model.entity.Supplier;
import by.accounting.medicines.model.entity.Unit;
import by.accounting.medicines.repository.SupplierRepository;
import by.accounting.medicines.util.ErrorResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public List<SupplierResponse> getAll() {
        return supplierRepository.findAll().stream().map(supplierMapper::toResponse).toList();
    }

    public SupplierResponse getById(Long id) {
        return supplierMapper.toResponse(findByIdOrThrow(id));
    }

    public SupplierResponse create(SupplierDto supplierDto) {
        return supplierMapper.toResponse(supplierRepository.save(supplierMapper.toEntity(supplierDto)));
    }

    public SupplierResponse update(Long id, SupplierDto supplierDto) {
        Supplier supplier = findByIdOrThrow(id);
        supplierMapper.updateEntity(supplierDto, supplier);
        return supplierMapper.toResponse(supplierRepository.save(supplier));
    }

    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }

    public Supplier findByIdOrThrow(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Supplier with id " + id + " not found", ErrorResponseUtil.SUPPLIER_NOT_FOUND));
    }
}
