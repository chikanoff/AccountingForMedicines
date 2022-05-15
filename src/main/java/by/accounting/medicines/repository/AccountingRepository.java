package by.accounting.medicines.repository;

import by.accounting.medicines.model.entity.Accounting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AccountingRepository extends JpaRepository<Accounting, Long> {

    List<Accounting> findByIncome(boolean income);
    List<Accounting> findByDateBetween(Date startDate, Date endDate);
}
