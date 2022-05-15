package by.accounting.medicines.controller;

import by.accounting.medicines.model.dto.entity.EmployeeDto;
import by.accounting.medicines.model.dto.response.EmployeeResponse;
import by.accounting.medicines.service.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/employees")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@Tag(name = "Admin employees configuration API")
@SecurityRequirement(name = "security")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getById(@PathVariable("id") Long id) {
        return employeeService.getById(id);
    }

    @PostMapping
    public EmployeeResponse create(@Valid @RequestBody EmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable("id") Long id,@Valid @RequestBody EmployeeDto employeeDto) {
        return employeeService.update(id, employeeDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
    }
}
