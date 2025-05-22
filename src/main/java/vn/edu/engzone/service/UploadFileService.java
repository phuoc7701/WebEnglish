package vn.edu.engzone.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadFileService {

    Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException {
        assert file.getOriginalFilename() != null;
        String publicValue = generatePublicValue(file.getOriginalFilename());
        log.info("Public Value: {}", publicValue);

        File fileUploaded = convertToTempFile(file);
        log.info("File uploaded path: {}", fileUploaded.getAbsolutePath());

        // Upload video
        Map uploadResult = cloudinary.uploader().upload(fileUploaded, ObjectUtils.asMap(
                "public_id", publicValue,
                "resource_type", "auto"
        ));

        log.info("Cloudinary upload result: {}", uploadResult);

        cleanDisk(fileUploaded);

        return uploadResult.get("secure_url").toString();
    }

    private File convertToTempFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = "." + getFileName(originalFilename)[1];

        Path tempPath = Files.createTempFile("upload_", extension);
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, tempPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        return tempPath.toFile();
    }

    private void cleanDisk(File file) {
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            log.error("Failed to delete temp file: {}", file.getAbsolutePath(), e);
        }
    }

    public String generatePublicValue(String originalName) {
        String fileName = getFileName(originalName)[0];
        return UUID.randomUUID().toString() + "_" + fileName;
    }

    public String[] getFileName(String originalName) {
        int lastDotIndex = originalName.lastIndexOf('.');
        return new String[]{
                originalName.substring(0, lastDotIndex),
                originalName.substring(lastDotIndex + 1)
        };
    }
}
