package vn.edu.engzone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.edu.engzone.dto.response.GeminiAIResponse;
import vn.edu.engzone.dto.response.GeminiSimpleResponse;

@Mapper(componentModel = "spring")
public interface GeminiMapper {
    GeminiMapper INSTANCE = Mappers.getMapper(GeminiMapper.class);

//    @Mapping(source = "candidates[0].content.parts[0].text", target = "text", qualifiedByName = "extractText")
//    GeminiSimpleResponse toSimpleDTO(GeminiAIResponse response);

    @Named("extractText")
    static String extractText(GeminiAIResponse response) {
        try {
            return response.getCandidates().get(0).getContent().getParts().get(0).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
