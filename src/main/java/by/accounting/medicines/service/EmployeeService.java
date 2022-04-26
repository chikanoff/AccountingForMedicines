package by.accounting.medicines.service;

import by.accounting.medicines.exception.ItemNotFoundException;
import by.accounting.medicines.mapper.DepartmentMapper;
import by.accounting.medicines.mapper.EmployeeMapper;
import by.accounting.medicines.model.dto.entity.DepartmentDto;
import by.accounting.medicines.model.dto.entity.EmployeeDto;
import by.accounting.medicines.model.dto.response.DepartmentResponse;
import by.accounting.medicines.model.dto.response.EmployeeResponse;
import by.accounting.medicines.model.entity.Department;
import by.accounting.medicines.model.entity.Employee;
import by.accounting.medicines.repository.DepartmentRepository;
import by.accounting.medicines.repository.EmployeeRepository;
import by.accounting.medicines.util.ErrorResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentService departmentService;

    public List<EmployeeResponse> getAll() {
        return employeeRepository.findAll().stream().map(employeeMapper::toResponse).toList();
    }

    public EmployeeResponse getById(Long id) {
        return employeeMapper.toResponse(findByIdOrThrow(id));
    }

    public EmployeeResponse create(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setDepartment(departmentService.findByIdOrThrow(employeeDto.getDepartmentId()));

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    public EmployeeResponse update(Long id, EmployeeDto employeeDto) {
        Employee employee = findByIdOrThrow(id);
        employeeMapper.updateEntity(employeeDto, employee);
        employee.setDepartment(departmentService.findByIdOrThrow(employeeDto.getDepartmentId()));
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee findByIdOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Employee with id " + id + " not found", ErrorResponseUtil.EMPLOYEE_NOT_FOUND));
    }
}
