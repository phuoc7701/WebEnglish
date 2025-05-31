package vn.edu.engzone.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class TestResponse {
    private String id;
    private String title;
    private String description;
    private String duration;
    private List<TestPartResponse> parts;
}
