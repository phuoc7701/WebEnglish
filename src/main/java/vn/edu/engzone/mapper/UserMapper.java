package vn.edu.engzone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.edu.engzone.dto.request.UserCreationRequest;
import vn.edu.engzone.dto.request.UserProfileRequest;
import vn.edu.engzone.dto.request.UserUpdateRequest;
import vn.edu.engzone.dto.response.UserProfileResponse;
import vn.edu.engzone.dto.response.UserResponse;
import vn.edu.engzone.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    UserProfileResponse toUserProfileResponse(User user);

    void updateUserProfile(@MappingTarget User user, UserProfileRequest request);

}
