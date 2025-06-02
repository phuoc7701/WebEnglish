package vn.edu.engzone.mapper;
import org.mapstruct.Mapper;
import vn.edu.engzone.dto.response.QuestionResponse;
import vn.edu.engzone.entity.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionResponse toDto(Question question);
}