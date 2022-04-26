package by.accounting.medicines.service;

import by.accounting.medicines.exception.ItemNotFoundException;
import by.accounting.medicines.mapper.AccountingMapper;
import by.accounting.medicines.mapper.MedicineMapper;
import by.accounting.medicines.model.dto.entity.AccountingDto;
import by.accounting.medicines.model.dto.entity.MedicineDto;
import by.accounting.medicines.model.dto.request.accounting.AccountingByDateBetweenRequest;
import by.accounting.medicines.model.dto.response.AccountingResponse;
import by.accounting.medicines.model.dto.response.MedicineResponse;
import by.accounting.medicines.model.entity.Accounting;
import by.accounting.medicines.model.entity.Medicine;
import by.accounting.medicines.repository.AccountingRepository;
import by.accounting.medicines.repository.MedicineRepository;
import by.accounting.medicines.util.ErrorResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountingService {
    private final AccountingRepository accountingRepository;
    private final AccountingMapper accountingMapper;
    private final MedicineService medicineService;
    private final EmployeeService employeeService;
    private final UserService userService;

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
        accounting.setEmployee(employeeService.findByIdOrThrow(accountingDto.getEmployeeId()));
        accounting.setUser(userService.findByIdOrThrow(accountingDto.getUserId()));
        accounting.setMedicines(new HashSet<>(medicineService.findAllByIds(accountingDto.getMedicineIds())));

        return accountingMapper.toResponse(accountingRepository.save(accounting));
    }

    public AccountingResponse update(Long id, AccountingDto accountingDto) {
        Accounting accounting = findByIdOrThrow(id);
        accountingMapper.updateEntity(accountingDto, accounting);
        accounting.setEmployee(employeeService.findByIdOrThrow(accountingDto.getEmployeeId()));
        accounting.setUser(userService.findByIdOrThrow(accountingDto.getUserId()));
        accounting.setMedicines(new HashSet<>(medicineService.findAllByIds(accountingDto.getMedicineIds())));

        return accountingMapper.toResponse(accountingRepository.save(accounting));
    }

    public List<AccountingResponse> findByDateBetween(AccountingByDateBetweenRequest req) {
        return accountingRepository.findByDateBetween(req.getStartDate(), req.getEndDate()).stream()
                .map(accountingMapper::toResponse).toList();
    }

    public void delete(Long id) {
        accountingRepository.deleteById(id);
    }

    public Accounting findByIdOrThrow(Long id) {
        return accountingRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Medicine with id " + id + " not found", ErrorResponseUtil.MEDICINE_NOT_FOUND));
    }
}
