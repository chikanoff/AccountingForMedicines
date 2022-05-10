package by.accounting.medicines.controller;

import by.accounting.medicines.model.dto.entity.UnitDto;
import by.accounting.medicines.model.dto.response.UnitResponse;
import by.accounting.medicines.service.UnitService;
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
@RequestMapping("/api/units")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USE')")
@Tag(name = "Admin units configuration API")
@SecurityRequirement(name = "security")
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @GetMapping
    public List<UnitResponse> getAllUnits() {
        return unitService.getAll();
    }

    @GetMapping("/{id}")
    public UnitResponse getUnitById(@PathVariable("id") Long id) {
        return unitService.getById(id);
    }

    @PostMapping
    public UnitResponse create(@Valid @RequestBody UnitDto unitDto) {
        return unitService.create(unitDto);
    }

    @PutMapping("/{id}")
    public UnitResponse update(@PathVariable("id") Long id, @Valid @RequestBody UnitDto unitDto) {
        return unitService.update(id, unitDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        unitService.delete(id);
    }
}
