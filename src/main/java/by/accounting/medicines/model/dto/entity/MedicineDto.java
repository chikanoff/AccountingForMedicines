package by.accounting.medicines.model.dto.entity;

import by.accounting.medicines.model.entity.Supplier;
import by.accounting.medicines.model.entity.Unit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MedicineDto {
    @NotEmpty
    private String name;

    @NotEmpty
    private String number;

    @NotNull
    private Long supplierId;

    @NotNull
    private Long unitId;
}
