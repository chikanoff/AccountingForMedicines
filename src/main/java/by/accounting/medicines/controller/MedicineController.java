package by.accounting.medicines.controller;

import by.accounting.medicines.model.dto.entity.MedicineDto;
import by.accounting.medicines.model.dto.response.MedicineResponse;
import by.accounting.medicines.model.entity.Medicine;
import by.accounting.medicines.service.MedicineService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/medicines")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@Tag(name = "Medicines configuration API")
@SecurityRequirement(name = "security")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;

    @GetMapping
    public Page<MedicineResponse> getPage(Pageable pageable) {
        return medicineService.getPage(pageable);
    }

    @GetMapping("/all")
    public List<MedicineResponse> getAll() {
        return medicineService.getAll();
    }

    @PostMapping
    public MedicineResponse create(@Valid @RequestBody MedicineDto medicineDto) {
        return medicineService.create(medicineDto);
    }

    @PutMapping("/{id}")
    public MedicineResponse update(@Valid @PathVariable("id") Long id, @Valid @RequestBody MedicineDto medicineDto) {
        return medicineService.update(id, medicineDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        medicineService.delete(id);
    }
}
