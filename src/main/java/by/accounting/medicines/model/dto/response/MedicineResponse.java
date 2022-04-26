package by.accounting.medicines.model.dto.response;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MedicineResponse {
    private Long id;

    private String number;

    private SupplierResponse supplier;

    private UnitResponse unit;
}
