package by.accounting.medicines.model.dto.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AccountingDto {

    private Long employeeId;

    private boolean income;

    private Long userId;

    private Set<MedicineAccoutingDto> medicines = new HashSet<>();
}
