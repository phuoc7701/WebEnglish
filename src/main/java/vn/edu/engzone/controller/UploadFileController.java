package vn.edu.engzone.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.engzone.service.UploadFileService;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadFileController {

    UploadFileService uploadFile;

    @PostMapping
    public String uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        return uploadFile.uploadFile(file);
    }
}
