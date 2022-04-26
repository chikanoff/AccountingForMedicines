package by.accounting.medicines.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {
    private Long id;

    private String fullName;

    private DepartmentResponse department;
}
