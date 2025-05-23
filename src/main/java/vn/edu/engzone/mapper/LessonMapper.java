package vn.edu.engzone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import vn.edu.engzone.dto.request.LessonCreateRequest;
import vn.edu.engzone.dto.request.LessonUpdateRequest;
import vn.edu.engzone.dto.response.LessonResponse;
import vn.edu.engzone.entity.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    Lesson toLesson(LessonCreateRequest request);

    LessonResponse toLessonResponse(Lesson lesson);

    @Mappings({
            @Mapping(target = "level", expression = "java(Level.valueOf(request.getLevel()))"),
            @Mapping(target = "type", expression = "java(LessonType.valueOf(request.getType()))"),
    })
    void updateLesson(@MappingTarget Lesson lesson, LessonUpdateRequest request);
}