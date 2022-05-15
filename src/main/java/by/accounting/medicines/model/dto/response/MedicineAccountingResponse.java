package by.accounting.medicines.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineAccountingResponse {
    private MedicineResponse medicine;
    private double count;
    private double price;
}
