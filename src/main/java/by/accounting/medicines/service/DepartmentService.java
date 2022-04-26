package by.accounting.medicines.service;

import by.accounting.medicines.exception.ItemNotFoundException;
import by.accounting.medicines.mapper.DepartmentMapper;
import by.accounting.medicines.mapper.SupplierMapper;
import by.accounting.medicines.model.dto.entity.DepartmentDto;
import by.accounting.medicines.model.dto.entity.SupplierDto;
import by.accounting.medicines.model.dto.response.DepartmentResponse;
import by.accounting.medicines.model.dto.response.SupplierResponse;
import by.accounting.medicines.model.entity.Department;
import by.accounting.medicines.model.entity.Supplier;
import by.accounting.medicines.repository.DepartmentRepository;
import by.accounting.medicines.util.ErrorResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public List<DepartmentResponse> getAll() {
        return departmentRepository.findAll().stream().map(departmentMapper::toResponse).toList();
    }

    public DepartmentResponse getById(Long id) {
        return departmentMapper.toResponse(findByIdOrThrow(id));
    }

    public DepartmentResponse create(DepartmentDto departmentDto) {
        return departmentMapper.toResponse(departmentRepository.save(departmentMapper.toEntity(departmentDto)));
    }

    public DepartmentResponse update(Long id, DepartmentDto departmentDto) {
        Department department = findByIdOrThrow(id);
        departmentMapper.updateEntity(departmentDto, department);
        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department findByIdOrThrow(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Department with id " + id + " not found", ErrorResponseUtil.DEPARTMENT_NOT_FOUND));
    }
}
