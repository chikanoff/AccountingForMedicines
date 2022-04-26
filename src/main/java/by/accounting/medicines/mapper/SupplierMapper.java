package by.accounting.medicines.mapper;

import by.accounting.medicines.model.dto.entity.SupplierDto;
import by.accounting.medicines.model.dto.response.SupplierResponse;
import by.accounting.medicines.model.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    @Mapping(target = "id", ignore = true)
    Supplier toEntity(SupplierDto dto);

    SupplierResponse toResponse(Supplier entity);

    @Mapping(target = "id", ignore = true)
    void updateEntity(SupplierDto dto, @MappingTarget Supplier entity);
}
