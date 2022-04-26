package by.accounting.medicines.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AccountingResponse {
    private Long id;

    private Date date;

    private EmployeeResponse employee;

    private boolean income;

    private UserResponse user;

    private Set<MedicineResponse> medicines = new HashSet<>();
}
