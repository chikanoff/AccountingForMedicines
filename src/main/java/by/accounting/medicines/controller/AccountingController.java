package by.accounting.medicines.controller;

import by.accounting.medicines.model.dto.entity.AccountingDto;
import by.accounting.medicines.model.dto.request.accounting.AccountingByDateBetweenRequest;
import by.accounting.medicines.model.dto.response.AccountingResponse;
import by.accounting.medicines.service.AccountingService;
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
@RequestMapping("/api/accountings")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@Tag(name = "Accountings configuration API")
@SecurityRequirement(name = "security")
@RequiredArgsConstructor
public class AccountingController {
    private final AccountingService accountingService;

    @GetMapping
    public List<AccountingResponse> getAll() {
        return accountingService.getAll();
    }

    @GetMapping("/{id}")
    public AccountingResponse getById(@PathVariable("id") Long id) {
        return accountingService.getById(id);
    }

    @GetMapping("/byDates")
    public List<AccountingResponse> getByDates(@RequestBody AccountingByDateBetweenRequest req) {
        return accountingService.findByDateBetween(req);
    }

    @PostMapping
    public AccountingResponse create(@Valid @RequestBody AccountingDto accountingDto) {
        return accountingService.create(accountingDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        accountingService.delete(id);
    }
}
