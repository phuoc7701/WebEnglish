package vn.edu.engzone.dto.request;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class TestPartRequest {
    private String id;
    private int partNumber;
    private String partTitle;
    private String testId;
    private List<QuestionRequest> questions;
}