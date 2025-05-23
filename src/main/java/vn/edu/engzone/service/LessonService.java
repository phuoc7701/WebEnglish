package vn.edu.engzone.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.engzone.dto.request.LessonCreateRequest;
import vn.edu.engzone.dto.request.LessonUpdateRequest;
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
    UploadFileService uploadFileService;

    public LessonResponse createLesson(LessonCreateRequest request) throws IOException {
        Lesson lesson = lessonMapper.toLesson(request);

        // Handle Video: upload file or use URL
        if (request.getVideoFile() != null && !request.getVideoFile().isEmpty()) {
            String videoUrl = uploadFileService.uploadFile(request.getVideoFile());
            lesson.setVideoUrl(videoUrl);
        } else if (request.getVideoUrl() != null && !request.getVideoUrl().isBlank()) {
            lesson.setVideoUrl(request.getVideoUrl());
        } else {
            throw new IllegalArgumentException("Must provide videoFile or videoUrl");
        }

        lesson.setCreatedBy(Optional.ofNullable(request.getCreatedBy()).orElse("admin"));
        lesson.setUpdatedBy(Optional.ofNullable(request.getUpdatedBy()).orElse("admin"));

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
            String videoUrl = uploadFileService.uploadFile(request.getVideoFile());
            lesson.setVideoUrl(videoUrl);
        } else if (request.getVideoUrl() != null && !request.getVideoUrl().isBlank()) {
            lesson.setVideoUrl(request.getVideoUrl());
        }

        lesson.setUpdatedAt(LocalDateTime.now());
        lesson.setUpdatedBy(
                request.getUpdatedBy() == null || request.getUpdatedBy().isBlank()
                        ? "admin"
                        : request.getUpdatedBy()
        );

        lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(lesson);
    }

    public void deleteLesson(String id) {
        lessonRepository.deleteById(id);
    }
}