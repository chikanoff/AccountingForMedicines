package by.accounting.medicines.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AccountingResponse {
    private Long id;

    @JsonFormat(pattern="dd-MM-yyyy", timezone = "Europe/Minsk")
    private Date date;

    private EmployeeResponse employee;

    private boolean income;

    private UserResponse user;

    private Set<MedicineAccountingResponse> medicines = new HashSet<>();
}
