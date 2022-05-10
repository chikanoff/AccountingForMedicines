package by.accounting.medicines.mapper;

import by.accounting.medicines.model.dto.entity.MedicineAccoutingDto;
import by.accounting.medicines.model.dto.entity.MedicineDto;
import by.accounting.medicines.model.entity.MedicineAccounting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicineAccountingMapper {
    @Mapping(target = "id", ignore = true)
    MedicineAccounting toEntity(MedicineAccoutingDto dto);
}
