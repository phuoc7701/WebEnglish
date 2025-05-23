package vn.edu.engzone.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) //message mà null thì không hiện
// chứa tất cả các Fill mà mình mong muốn để chuẩn hóa cho API của mình
public class ApiResponse <T> {
    int code = 1000;
    String message;
    T result;
}
