package vn.edu.english.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritingPracticeRequest {
    private String title;
    private String description;
    private String level;
    private Integer duration;
    private String updatedBy;
}
