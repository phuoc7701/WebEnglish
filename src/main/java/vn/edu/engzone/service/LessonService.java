package vn.edu.engzone.service;

import vn.edu.engzone.dto.request.LessonCreateRequest;
import vn.edu.engzone.dto.response.LessonResponse;

import java.util.List;

public interface LessonService {
    LessonResponse createLesson(LessonCreateRequest request);

    List<LessonResponse> getAllLessons();

    LessonResponse getLessonById(String id);

    LessonResponse updateLesson(String id, LessonCreateRequest request);

    void deleteLesson(String id);
}
