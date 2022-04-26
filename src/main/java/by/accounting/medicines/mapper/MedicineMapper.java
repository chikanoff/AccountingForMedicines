package by.accounting.medicines.mapper;

import by.accounting.medicines.model.dto.entity.MedicineDto;
import by.accounting.medicines.model.dto.response.MedicineResponse;
import by.accounting.medicines.model.entity.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedicineMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "unit", ignore = true)
    Medicine toEntity(MedicineDto dto);

    MedicineResponse toResponse(Medicine entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "unit", ignore = true)
    void updateEntity(MedicineDto dto, @MappingTarget Medicine entity);
}
