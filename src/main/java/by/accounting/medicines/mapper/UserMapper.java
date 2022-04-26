package by.accounting.medicines.mapper;

import by.accounting.medicines.model.dto.request.user.CreateUserRequest;
import by.accounting.medicines.model.dto.request.user.UpdateUserRequest;
import by.accounting.medicines.model.dto.response.UserResponse;
import by.accounting.medicines.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User createUserRequestToUser(CreateUserRequest req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserWithUpdateUserRequest(UpdateUserRequest req, @MappingTarget User user);

    UserResponse userToUserResponse(User user);
}
