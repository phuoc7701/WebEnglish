package vn.edu.engzone.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class TestPartRequest {
    private int partNumber;
    private String partTitle;
    private List<QuestionRequest> questions;
}