package by.accounting.medicines.model.dto.request.accounting;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountingByDateBetweenRequest {
    private Date startDate;
    private Date endDate;
}
