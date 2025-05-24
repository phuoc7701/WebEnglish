package vn.edu.engzone.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.engzone.dto.response.CloudinaryResponse;
import vn.edu.engzone.service.CloudinaryService;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadFileController {

    CloudinaryService cloudinaryService;

    @PostMapping
    public CloudinaryResponse uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        return cloudinaryService.uploadFile(file);
    }
}
