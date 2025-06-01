package vn.edu.engzone.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class TestPartResponse {
    private String id;
    private int partNumber;
    private String partTitle;
    private List<QuestionResponse> questions;
}
