package vn.edu.engzone.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.engzone.dto.response.CloudinaryResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryService {

    Cloudinary cloudinary;

    public CloudinaryResponse uploadFile(MultipartFile file, String folder, String id, String prefix) throws IOException {
        assert file.getOriginalFilename() != null;
        String publicValue = generatePublicValue(file.getOriginalFilename(), id, prefix);

        File fileUploaded = convertToTempFile(file);

        // Thêm upload_preset vào tham số
        Map uploadResultMap = cloudinary.uploader().upload(fileUploaded, ObjectUtils.asMap(
                "public_id", publicValue,
                "folder", folder,
                "resource_type", "auto"
        ));

        cleanDisk(fileUploaded);

        String actualPublicId = (String) uploadResultMap.get("public_id");
        String secureUrl = (String) uploadResultMap.get("secure_url");

        if (actualPublicId == null || secureUrl == null) {
            log.error("Cloudinary upload failed to return public_id or secure_url. Result: {}", uploadResultMap);
            throw new IOException("Cloudinary upload failed, missing public_id or secure_url in response.");
        }

        return new CloudinaryResponse(actualPublicId, secureUrl);
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

    public String generatePublicValue(String originalName, String id, String prefix) {
        String fileName = getFileName(originalName)[0];
        if (fileName.isEmpty()) {
            fileName = "untitled";
        }
        return prefix + "_" + id + "_" + fileName;
    }

    public String[] getFileName(String originalName) {
        int lastDotIndex = originalName.lastIndexOf('.');
        return new String[]{
                originalName.substring(0, lastDotIndex),
                originalName.substring(lastDotIndex + 1)
        };
    }

    public void deleteFile(String publicId) {
        try {
            Map result = this.cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "video"));
//            log.info("Cloudinary delete result for publicId {}: {}", publicId, result);
            if (result.get("result") == null || !result.get("result").equals("ok")) {
                log.warn("Failed to delete file on Cloudinary or file not found. Public ID: {}, Result: {}", publicId, result);
            }
        } catch (Exception e) {
            log.error("Error during Cloudinary file deletion for publicId: {}", publicId, e);
            throw new RuntimeException("Failed to delete file from Cloudinary, publicId: " + publicId, e);
        }
    }
}
