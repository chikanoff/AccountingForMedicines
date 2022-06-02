package by.accounting.medicines.model.dto.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AccountingDto {

    private Long employeeId;

    private boolean income;

    private Long userId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    private Set<MedicineAccoutingDto> medicines = new HashSet<>();
}
