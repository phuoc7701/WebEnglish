package vn.edu.engzone.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.engzone.dto.request.LessonCreateRequest;
import vn.edu.engzone.dto.request.LessonUpdateRequest;
import vn.edu.engzone.dto.response.CloudinaryResponse;
import vn.edu.engzone.dto.response.LessonResponse;
import vn.edu.engzone.entity.Lesson;
import vn.edu.engzone.enums.CommentType;
import vn.edu.engzone.enums.LessonType;
import vn.edu.engzone.enums.Level;
import vn.edu.engzone.mapper.LessonMapper;
import vn.edu.engzone.repository.CommentRepository;
import vn.edu.engzone.repository.LessonRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LessonService {
    LessonRepository lessonRepository;
    LessonMapper lessonMapper;
    CloudinaryService cloudinaryService;
    CommentRepository commentRepository;
    AuthenticationService authenticationService;

    static String VIDEO_FOLDER = "engzone/lesson/video";
    static String LESSON_VIDEO_PREFIX = "lesson_video";

    public LessonResponse createLesson(LessonCreateRequest request) throws IOException {
        Lesson lesson = lessonMapper.toLesson(request);

        String lessonId = UUID.randomUUID().toString();
        lesson.setLessonId(lessonId);

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

        // Handle video upload using lesson ID
        if (request.getVideoFile() != null && !request.getVideoFile().isEmpty()) {
            CloudinaryResponse uploadDetails = cloudinaryService.uploadFile(
                    request.getVideoFile(),
                    VIDEO_FOLDER,
                    lesson.getLessonId(),
                    LESSON_VIDEO_PREFIX
            );

            lesson.setVideoUrl(uploadDetails.getSecureUrl());
            lesson.setVideoFileId(uploadDetails.getPublicId());

            lessonRepository.save(lesson);
        } else {
            lessonRepository.delete(lesson); // Rollback if no video
            throw new IllegalArgumentException("Must provide videoFile");
        }

        return lessonMapper.toLessonResponse(lesson);
    }

    public LessonResponse getLessonById(String id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        return lessonMapper.toLessonResponse(lesson);
    }

    public List<LessonResponse> getLessonsByTypeAndLevel(LessonType type, Level level) {
        return lessonRepository.findByTypeAndLevel(type, level).stream()
                .map(lessonMapper::toLessonResponse)
                .collect(Collectors.toList());
    }

    public List<LessonResponse> getAllLessons() {
        return lessonRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt")).stream()
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
//                    log.info("Successfully deleted old video with publicId: {}", oldVideoFileId);
                } catch (Exception e) {
                    log.error("Could not delete old video with publicId: {}. Continuing with new video upload.", oldVideoFileId, e);
                }
            }

            CloudinaryResponse uploadDetails = cloudinaryService.uploadFile(
                    request.getVideoFile(),
                    VIDEO_FOLDER,
                    lesson.getLessonId(),
                    LESSON_VIDEO_PREFIX

            );

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
            updater = "unknown_user";
        }
        lesson.setUpdatedBy(updater);

        lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(lesson);
    }

    public void deleteLesson(String id) {
        Optional<Lesson> lessonOpt = lessonRepository.findById(id);
        if (lessonOpt.isPresent()) {
            Lesson lesson = lessonOpt.get();
            String videoFileId = lesson.getVideoFileId();
            if (videoFileId != null && !videoFileId.isBlank()) {
                try {
                    cloudinaryService.deleteFile(videoFileId);
                    log.info("Successfully deleted video with publicId: {}", videoFileId);
                } catch (Exception e) {
                    log.error("Could not delete video with publicId: {}. Continuing with lesson deletion.", videoFileId, e);
                }
            }
            commentRepository.deleteByReferenceIdAndCommentType(id, CommentType.LESSON);
            lessonRepository.deleteById(id);
        } else {
            throw new RuntimeException("Lesson not found");
        }
    }
}