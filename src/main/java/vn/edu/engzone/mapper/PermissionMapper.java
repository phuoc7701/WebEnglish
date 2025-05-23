package vn.edu.engzone.mapper;

import vn.edu.engzone.dto.request.PermissionRequest;
import vn.edu.engzone.dto.response.PermissionResponse;
import vn.edu.engzone.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
