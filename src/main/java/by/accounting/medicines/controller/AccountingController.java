package by.accounting.medicines.controller;

import by.accounting.medicines.model.dto.entity.AccountingDto;
import by.accounting.medicines.model.dto.request.accounting.AccountingByDateBetweenRequest;
import by.accounting.medicines.model.dto.response.AccountingResponse;
import by.accounting.medicines.model.entity.Accounting;
import by.accounting.medicines.service.AccountingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
    public List<AccountingResponse> getByDates(@RequestParam(value="startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                               @RequestParam(value="endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return accountingService.findByDateBetween(startDate, endDate);
    }

    @GetMapping("/incomes")
    public List<AccountingResponse> getIncomes() {
        return accountingService.findByIncome(true);
    }

    @GetMapping("/consumptions")
    public List<AccountingResponse> getConsumptions() {
        return accountingService.findByIncome(false);
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
