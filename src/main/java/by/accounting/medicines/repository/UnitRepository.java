package by.accounting.medicines.repository;

import by.accounting.medicines.model.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Unit findByName(String name);
}
