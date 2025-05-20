package vn.edu.engzone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.edu.engzone.dto.request.LessonCreateRequest;
import vn.edu.engzone.dto.response.LessonResponse;
import vn.edu.engzone.entity.Lesson;
import vn.edu.engzone.enums.LessonType;
import vn.edu.engzone.enums.Level;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    Lesson toLesson(LessonCreateRequest request);

    LessonResponse toLessonResponse(Lesson lesson);

//    void updateLesson(@MappingTarget Lesson lesson, LessonUpdateRequest request);
}
