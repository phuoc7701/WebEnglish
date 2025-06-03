package vn.edu.engzone.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    private static final Logger logger = LoggerFactory.getLogger(EmailConfig.class);

    @Value("${spring.mail.username}")
    private String email;

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.refresh-token}")
    private String refreshToken;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(email);

        // Set up OAuth2 access token
        try {
            GoogleClientSecrets.Details details = new GoogleClientSecrets.Details();
            details.setClientId(clientId);
            details.setClientSecret(clientSecret);

            GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
            clientSecrets.setWeb(details);

            Credential credential = new GoogleCredential.Builder()
                    .setTransport(new NetHttpTransport())
                    .setJsonFactory(JacksonFactory.getDefaultInstance())
                    .setClientSecrets(clientSecrets)
                    .build()
                    .setRefreshToken(refreshToken);

            credential.refreshToken();
            String accessToken = credential.getAccessToken();
            if (accessToken == null || accessToken.isEmpty()) {
                throw new RuntimeException("Access token is null or empty after refresh");
            }
            mailSender.setPassword(accessToken);
            logger.info("OAuth2 access token generated successfully: {}", accessToken);
        } catch (Exception e) {
            logger.error("Failed to configure OAuth2 for email", e);
            throw new RuntimeException("Failed to configure OAuth2 for email", e);
        }

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.auth.mechanisms", "XOAUTH2"); // Bắt buộc cho Gmail SMTP với OAuth2
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "3000");
        props.put("mail.smtp.writetimeout", "5000");

        return mailSender;
    }
}
