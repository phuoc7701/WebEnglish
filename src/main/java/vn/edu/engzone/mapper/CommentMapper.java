package vn.edu.engzone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.edu.engzone.dto.request.CommentCreateRequest;
import vn.edu.engzone.dto.request.CommentUpdateRequest;
import vn.edu.engzone.dto.response.CommentResponse;
import vn.edu.engzone.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toComment(CommentCreateRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    CommentResponse toCommentResponse(Comment comment);

    void updateComment(CommentUpdateRequest request, @MappingTarget Comment comment);
}