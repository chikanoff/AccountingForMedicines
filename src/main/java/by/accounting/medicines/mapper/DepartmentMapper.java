package by.accounting.medicines.mapper;

import by.accounting.medicines.model.dto.entity.DepartmentDto;
import by.accounting.medicines.model.dto.response.DepartmentResponse;
import by.accounting.medicines.model.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    @Mapping(target = "id", ignore = true)
    Department toEntity(DepartmentDto departmentDto);

    DepartmentResponse toResponse(Department department);

    @Mapping(target = "id", ignore = true)
    void updateEntity(DepartmentDto departmentDto, @MappingTarget Department department);
}
