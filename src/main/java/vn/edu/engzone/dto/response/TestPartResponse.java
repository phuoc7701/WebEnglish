package vn.edu.engzone.dto.response;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class TestPartResponse {
    private String id;
    private int partNumber;
    private String partTitle;
    private UUID testId;
    private List<QuestionResponse> questions;
}
