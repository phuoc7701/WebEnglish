package vn.edu.engzone.mapper;

import vn.edu.engzone.dto.request.RoleRequest;
import vn.edu.engzone.dto.response.RoleResponse;
import vn.edu.engzone.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
