package by.accounting.medicines.model.dto.entity;

import by.accounting.medicines.model.entity.Department;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class EmployeeDto {
    private String fullName;

    private Long departmentId;
}
