package vn.edu.engzone.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.engzone.dto.request.LessonCreateRequest;
import vn.edu.engzone.dto.response.LessonResponse;
import vn.edu.engzone.entity.Lesson;
import vn.edu.engzone.enums.LessonType;
import vn.edu.engzone.enums.Level;
import vn.edu.engzone.mapper.LessonMapper;
import vn.edu.engzone.repository.LessonRepository;
import vn.edu.engzone.service.LessonService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LessonServiceImpl implements LessonService {
    LessonRepository lessonRepository;
    LessonMapper lessonMapper;

    @Override
    public LessonResponse createLesson(LessonCreateRequest request) {
        Lesson lesson = lessonMapper.toLesson(request);

        if (request.getCreatedBy() == null || request.getCreatedBy().isBlank()) {
            lesson.setCreatedBy("admin"); // hoặc "system"
        } else {
            lesson.setCreatedBy(request.getCreatedBy());
        }

        if (request.getUpdatedBy() == null || request.getUpdatedBy().isBlank()) {
            lesson.setUpdatedBy("admin"); // hoặc "system"
        } else {
            lesson.setUpdatedBy(request.getUpdatedBy());
        }

        lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(lesson);
    }

    @Override
    public LessonResponse getLessonById(String id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        return lessonMapper.toLessonResponse(lesson);
    }

    @Override
    public List<LessonResponse> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(lessonMapper::toLessonResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LessonResponse updateLesson(String id, LessonCreateRequest request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setLevel(Level.valueOf(request.getLevel()));
        lesson.setType(LessonType.valueOf(request.getType()));
        lesson.setImageUrl(request.getImageUrl());
        lesson.setPackageRequired(request.isPackageRequired());
        lesson.setUpdatedAt(LocalDateTime.now());
        if (lesson.getUpdatedBy() == null) {
            lesson.setUpdatedBy("admin");
        } else {
            lesson.setUpdatedBy(request.getUpdatedBy());
        }

        lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(lesson);
    }

    @Override
    public void deleteLesson(String id) {
        lessonRepository.deleteById(id);
    }
}
