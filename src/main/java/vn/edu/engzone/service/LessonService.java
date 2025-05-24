package vn.edu.engzone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.engzone.dto.request.LessonCreateRequest;
import vn.edu.engzone.dto.request.LessonUpdateRequest;
import vn.edu.engzone.dto.response.CloudinaryResponse;
import vn.edu.engzone.dto.response.LessonResponse;
import vn.edu.engzone.entity.Lesson;
import vn.edu.engzone.mapper.LessonMapper;
import vn.edu.engzone.repository.LessonRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LessonService {
    LessonRepository lessonRepository;
    LessonMapper lessonMapper;
    CloudinaryService cloudinaryService;
    ObjectMapper objectMapper;
    AuthenticationService authenticationService;

    public LessonResponse createLesson(LessonCreateRequest request) throws IOException {
        Lesson lesson = lessonMapper.toLesson(request);

        // Handle Video: upload file or use URL
        if (request.getVideoFile() != null && !request.getVideoFile().isEmpty()) {
            CloudinaryResponse uploadDetails = cloudinaryService.uploadFile(request.getVideoFile());

            lesson.setVideoUrl(uploadDetails.getSecureUrl());
            lesson.setVideoFileId(uploadDetails.getPublicId());

        } else {
            throw new IllegalArgumentException("Must provide videoFile or videoUrl");
        }

        String authenticatedUser = authenticationService.getCurrentAuthenticatedUsername();
        String creator;
        String initialUpdater;

        if (authenticatedUser != null) {
            creator = authenticatedUser;
        } else {
            log.warn("The creator of the lesson is unknown. Set the default value to 'unknown_user'.");
            creator = "unknown_user";
        }
        lesson.setCreatedBy(creator);


        initialUpdater = creator;

        lesson.setUpdatedBy(initialUpdater);

        lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(lesson);
    }

    public LessonResponse getLessonById(String id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        return lessonMapper.toLessonResponse(lesson);
    }

    public List<LessonResponse> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(lessonMapper::toLessonResponse)
                .collect(Collectors.toList());
    }

    public LessonResponse updateLesson(String id, LessonUpdateRequest request) throws IOException {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        lessonMapper.updateLesson(lesson, request);

        // Handle Video: upload file or use URL
        if (request.getVideoFile() != null && !request.getVideoFile().isEmpty()) {

            String oldVideoFileId = lesson.getVideoFileId();
            if (oldVideoFileId != null && !oldVideoFileId.isBlank()) {
                try {
                    cloudinaryService.deleteFile(oldVideoFileId);
                    log.info("Successfully deleted old video with publicId: {}", oldVideoFileId);
                } catch (Exception e) {
                    log.error("Could not delete old video with publicId: {}. Continuing with new video upload.", oldVideoFileId, e);
                }
            }

            CloudinaryResponse uploadDetails = cloudinaryService.uploadFile(request.getVideoFile());

            lesson.setVideoUrl(uploadDetails.getSecureUrl());
            lesson.setVideoFileId(uploadDetails.getPublicId());
        }

        lesson.setUpdatedAt(LocalDateTime.now());

        String authenticatedUser = authenticationService.getCurrentAuthenticatedUsername();
        String updater;

        if (authenticatedUser != null) {
            updater = authenticatedUser;
        } else {
            log.warn("The updater of the lesson is unknown. Set the default value to 'unknown_user'.");
            updater = "unknown_user"; // Fallback
        }
        lesson.setUpdatedBy(updater);

        lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(lesson);
    }

    public void deleteLesson(String id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        String videoFileId = lesson.get().getVideoFileId();
        if (videoFileId != null && !videoFileId.isBlank()) {
            try {
                cloudinaryService.deleteFile(videoFileId);
                log.info("Successfully deleted old video with publicId: {}", videoFileId);
            } catch (Exception e) {
                log.error("Could not delete old video with publicId: {}. Continuing with new video upload.", videoFileId, e);
            }
        }
        lessonRepository.deleteById(id);
    }
}