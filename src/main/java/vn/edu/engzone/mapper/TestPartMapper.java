package vn.edu.engzone.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.engzone.dto.request.TestPartRequest;
import vn.edu.engzone.dto.response.TestPartResponse;
import vn.edu.engzone.entity.TestPart;

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public interface TestPartMapper {
    @Mapping(target = "testId", source = "test.id")
    TestPartResponse toDto(TestPart testPart);

    TestPart toEntity(TestPartRequest dto);
}

