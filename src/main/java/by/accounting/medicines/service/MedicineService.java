package by.accounting.medicines.service;

import by.accounting.medicines.exception.ItemNotFoundException;
import by.accounting.medicines.mapper.EmployeeMapper;
import by.accounting.medicines.mapper.MedicineMapper;
import by.accounting.medicines.model.dto.entity.EmployeeDto;
import by.accounting.medicines.model.dto.entity.MedicineDto;
import by.accounting.medicines.model.dto.response.EmployeeResponse;
import by.accounting.medicines.model.dto.response.MedicineResponse;
import by.accounting.medicines.model.entity.Employee;
import by.accounting.medicines.model.entity.Medicine;
import by.accounting.medicines.repository.EmployeeRepository;
import by.accounting.medicines.repository.MedicineRepository;
import by.accounting.medicines.util.ErrorResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;
    private final SupplierService supplierService;
    private final UnitService unitService;

    public List<MedicineResponse> getAll() {
        return medicineRepository.findAll().stream().map(medicineMapper::toResponse).toList();
    }

    public Page<MedicineResponse> getPage(Pageable pageable) {
        return medicineRepository.findAll(pageable).map(medicineMapper::toResponse);
    }

    public MedicineResponse getById(Long id) {
        return medicineMapper.toResponse(findByIdOrThrow(id));
    }

    public MedicineResponse create(MedicineDto medicineDto) {
        Medicine medicine = medicineMapper.toEntity(medicineDto);

        medicine.setSupplier(supplierService.findByIdOrThrow(medicineDto.getSupplierId()));
        medicine.setUnit(unitService.findByIdOrThrow(medicineDto.getUnitId()));

        return medicineMapper.toResponse(medicineRepository.save(medicine));
    }

    public MedicineResponse update(Long id, MedicineDto medicineDto) {
        Medicine medicine = findByIdOrThrow(id);
        medicineMapper.updateEntity(medicineDto, medicine);

        medicine.setSupplier(supplierService.findByIdOrThrow(medicineDto.getSupplierId()));
        medicine.setUnit(unitService.findByIdOrThrow(medicineDto.getUnitId()));

        return medicineMapper.toResponse(medicineRepository.save(medicine));
    }

    public void delete(Long id) {
        medicineRepository.deleteById(id);
    }

    public List<Medicine> findAllByIds(Set<Long> ids) {
        return medicineRepository.findAllById(ids);
    }

    public Medicine findByIdOrThrow(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Medicine with id " + id + " not found", ErrorResponseUtil.MEDICINE_NOT_FOUND));
    }
}
