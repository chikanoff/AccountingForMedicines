package by.accounting.medicines.controller;

import by.accounting.medicines.model.dto.entity.DepartmentDto;
import by.accounting.medicines.model.dto.response.DepartmentResponse;
import by.accounting.medicines.service.DepartmentService;
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
@RequestMapping("/api/admin/departments")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Tag(name = "Admin departments configuration API")
@SecurityRequirement(name = "security")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentResponse> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable("id") Long id) {
        return departmentService.getById(id);
    }

    @PostMapping
    public DepartmentResponse create(@Valid @RequestBody DepartmentDto departmentDto) {
        return departmentService.create(departmentDto);
    }

    @PutMapping("{id}")
    public DepartmentResponse update(@PathVariable("id") Long id, @Valid @RequestBody DepartmentDto departmentDto) {
        return departmentService.update(id, departmentDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        departmentService.delete(id);
    }
}
