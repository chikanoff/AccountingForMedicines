package by.accounting.medicines.mapper;

import by.accounting.medicines.model.dto.entity.AccountingDto;
import by.accounting.medicines.model.dto.response.AccountingResponse;
import by.accounting.medicines.model.entity.Accounting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountingMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "medicineAccountings", ignore = true)
    Accounting toEntity(AccountingDto accountingDto);

    @Mapping(target = "medicines", ignore = true)
    AccountingResponse toResponse(Accounting accounting);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "medicineAccountings", ignore = true)
    void updateEntity(AccountingDto accountingDto, @MappingTarget Accounting accounting);
}
