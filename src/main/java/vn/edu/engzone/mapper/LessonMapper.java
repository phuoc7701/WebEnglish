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
        @Mappings({
                @Mapping(target = "level", expression = "java(vn.edu.engzone.enums.Level.valueOf(request.getLevel().toUpperCase()))"),
                @Mapping(target = "type", expression = "java(vn.edu.engzone.enums.LessonType.valueOf(request.getType().toUpperCase()))")
        })
        Lesson toLesson(LessonCreateRequest request);

        @Mapping(target = "isPackageRequired", source = "packageRequired")
        LessonResponse toLessonResponse(Lesson lesson);

        @Mappings({
                @Mapping(target = "level", expression = "java(vn.edu.engzone.enums.Level.valueOf(request.getLevel().toUpperCase()))"),
                @Mapping(target = "type", expression = "java(vn.edu.engzone.enums.LessonType.valueOf(request.getType().toUpperCase()))")
        })
        void updateLesson(@MappingTarget Lesson lesson, LessonUpdateRequest request);
    }