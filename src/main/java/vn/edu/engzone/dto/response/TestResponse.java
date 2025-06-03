package vn.edu.engzone.dto.response;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class TestResponse {
    private String id;
    private String title;
    private String description;
    private int duration;
    private List<TestPartResponse> parts;
}
