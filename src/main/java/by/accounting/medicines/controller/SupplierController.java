package by.accounting.medicines.controller;

import by.accounting.medicines.model.dto.entity.SupplierDto;
import by.accounting.medicines.model.dto.response.SupplierResponse;
import by.accounting.medicines.service.SupplierService;
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
@RequestMapping("/api/admin/suppliers")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Tag(name = "Admin suppliers configuration API")
@SecurityRequirement(name = "security")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    public List<SupplierResponse> getAll() {
        return supplierService.getAll();
    }

    @GetMapping("/{id}")
    public SupplierResponse getById(@PathVariable("id") Long id) {
        return supplierService.getById(id);
    }

    @PostMapping
    public SupplierResponse create(@Valid @RequestBody SupplierDto supplierDto) {
        return supplierService.create(supplierDto);
    }

    @PutMapping("/{id}")
    public SupplierResponse update(@PathVariable("id") Long id, @Valid @RequestBody SupplierDto supplierDto) {
        return supplierService.update(id, supplierDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        supplierService.delete(id);
    }
}
