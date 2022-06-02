package by.accounting.medicines.service;

import by.accounting.medicines.exception.ItemNotFoundException;
import by.accounting.medicines.mapper.AccountingMapper;
import by.accounting.medicines.mapper.MedicineAccountingMapper;
import by.accounting.medicines.mapper.MedicineMapper;
import by.accounting.medicines.model.dto.entity.AccountingDto;
import by.accounting.medicines.model.dto.entity.MedicineAccoutingDto;
import by.accounting.medicines.model.dto.entity.MedicineDto;
import by.accounting.medicines.model.dto.request.accounting.AccountingByDateBetweenRequest;
import by.accounting.medicines.model.dto.response.AccountingResponse;
import by.accounting.medicines.model.dto.response.MedicineResponse;
import by.accounting.medicines.model.entity.Accounting;
import by.accounting.medicines.model.entity.Medicine;
import by.accounting.medicines.model.entity.MedicineAccounting;
import by.accounting.medicines.repository.AccountingRepository;
import by.accounting.medicines.repository.MedicineAccountingRepository;
import by.accounting.medicines.repository.MedicineRepository;
import by.accounting.medicines.util.ErrorResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountingService {
    private final AccountingRepository accountingRepository;
    private final AccountingMapper accountingMapper;
    private final MedicineService medicineService;
    private final EmployeeService employeeService;
    private final UserService userService;
    private final MedicineAccountingMapper medicineAccountingMapper;
    private final MedicineAccountingRepository medicineAccountingRepository;

    public List<AccountingResponse> getAll() {
        return accountingRepository.findAll().stream().map(accountingMapper::toResponse).toList();
    }

    public Page<AccountingResponse> getPage(Pageable pageable) {
        return accountingRepository.findAll(pageable).map(accountingMapper::toResponse);
    }

    public AccountingResponse getById(Long id) {
        return accountingMapper.toResponse(findByIdOrThrow(id));
    }

    public AccountingResponse create(AccountingDto accountingDto) {
        Accounting accounting = accountingMapper.toEntity(accountingDto);
        Date date = Date.from(accountingDto.getDate()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        accounting.setDate(date);
        if (!accountingDto.isIncome()) {
            accounting.setEmployee(employeeService.findByIdOrThrow(accountingDto.getEmployeeId()));
        }
        accounting.setUser(userService.findByIdOrThrow(accountingDto.getUserId()));

        Accounting saved = accountingRepository.save(accounting);

        accountingDto.getMedicines().forEach(x -> {
            MedicineAccounting ma = medicineAccountingMapper.toEntity(x);
            ma.setAccounting(saved);
            ma.setMedicine(medicineService.findByIdOrThrow(x.getMedicineId()));
            medicineAccountingRepository.save(ma);
        });

        return accountingMapper.toResponse(accountingRepository.getById(saved.getId()));
    }

    public List<AccountingResponse> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        Date start = Date.from(startDate
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Date end = Date.from(endDate
                .atZone(ZoneId.systemDefault())
                .toInstant());
        List<AccountingResponse> res = accountingRepository.findByDateBetween(start, end).stream()
                .map(accountingMapper::toResponse).toList();
        res.forEach(x -> x.setMedicines(new HashSet<>(medicineAccountingRepository.findByAccountingId(x.getId()).stream().map(medicineAccountingMapper::toResponse).toList())));

        return res;
    }

    public void delete(Long id) {
        accountingRepository.deleteById(id);
    }

    public Accounting findByIdOrThrow(Long id) {
        return accountingRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Medicine with id " + id + " not found", ErrorResponseUtil.MEDICINE_NOT_FOUND));
    }

    public List<AccountingResponse> findByIncome(boolean income) {
        List<AccountingResponse> accounting = accountingRepository.findByIncome(income).stream().map(accountingMapper::toResponse).toList();
        accounting.forEach(x -> x.setMedicines(new HashSet<>(medicineAccountingRepository.findByAccountingId(x.getId()).stream().map(medicineAccountingMapper::toResponse).toList())));
        return accounting;
    }
}
