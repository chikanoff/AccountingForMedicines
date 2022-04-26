package by.accounting.medicines.mapper;

import by.accounting.medicines.model.dto.entity.EmployeeDto;
import by.accounting.medicines.model.dto.response.EmployeeResponse;
import by.accounting.medicines.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    Employee toEntity(EmployeeDto employeeDto);

    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    void updateEntity(EmployeeDto employeeDto, @MappingTarget Employee employee);
}
