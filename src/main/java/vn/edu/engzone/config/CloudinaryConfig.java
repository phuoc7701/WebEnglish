package vn.edu.engzone.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    String cloudName;
    String cloudApiKey;
    String cloudApiSecret;
    String cloudUploadPreset;

    public CloudinaryConfig(Dotenv dotenv) {
        this.cloudName = dotenv.get("CLOUD_NAME");
        this.cloudApiKey = dotenv.get("CLOUD_API_KEY");
        this.cloudApiSecret = dotenv.get("CLOUD_API_SECRET");
        this.cloudUploadPreset = dotenv.get("CLOUD_UPLOAD_PRESET");

        if (cloudName == null || cloudApiKey == null || cloudApiSecret == null || cloudUploadPreset == null) {
            throw new IllegalStateException("Missing Cloudinary configuration in .env file");
        }
        // Thêm log để kiểm tra
        System.out.println("Cloudinary Config - cloudName: " + cloudName);
        System.out.println("Cloudinary Config - cloudApiKey: " + cloudApiKey);
        System.out.println("Cloudinary Config - cloudApiSecret: " + cloudApiSecret);
        System.out.println("Cloudinary Config - cloudUploadPreset: " + cloudUploadPreset);
    }

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", cloudApiKey);
        config.put("api_secret", cloudApiSecret);
        config.put("upload_preset", cloudUploadPreset);
        return new Cloudinary(config);
    }
}