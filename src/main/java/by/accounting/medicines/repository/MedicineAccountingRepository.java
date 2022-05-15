package by.accounting.medicines.repository;

import by.accounting.medicines.model.entity.MedicineAccounting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineAccountingRepository extends JpaRepository<MedicineAccounting, Long> {
    List<MedicineAccounting> findByAccountingId(Long accountingId);
}
