package vn.edu.engzone.mapper;

import vn.edu.engzone.dto.request.UserCreationRequest;
import vn.edu.engzone.dto.request.UserUpdateRequest;
import vn.edu.engzone.dto.response.UserResponse;
import vn.edu.engzone.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request); //medthod này nhận parameter request và trả về 1 class

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
