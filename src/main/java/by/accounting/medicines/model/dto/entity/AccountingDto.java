package by.accounting.medicines.model.dto.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AccountingDto {
    private Date date;

    private Long employeeId;

    private boolean income;

    private Long userId;

    private Set<Long> medicineIds = new HashSet<>();
}
