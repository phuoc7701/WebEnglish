package vn.edu.engzone.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class TestRequest {
    private String title;
    private String description;
    private String duration;
    private List<TestPartRequest> parts;
}
