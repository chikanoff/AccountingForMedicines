package by.accounting.medicines.mapper;

import by.accounting.medicines.model.dto.entity.UnitDto;
import by.accounting.medicines.model.dto.response.UnitResponse;
import by.accounting.medicines.model.entity.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    @Mapping(target = "id", ignore = true)
    Unit toEntity(UnitDto dto);

    UnitResponse toResponse(Unit entity);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UnitDto dto, @MappingTarget Unit entity);
}
