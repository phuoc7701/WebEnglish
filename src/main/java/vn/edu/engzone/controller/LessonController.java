
package vn.edu.engzone.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.engzone.dto.response.LessonResponse;
import vn.edu.engzone.dto.response.QuizQuestionResponse;
import vn.edu.engzone.entity.QuizQuestion;
import vn.edu.engzone.enums.LessonType;
import vn.edu.engzone.enums.Level;
import vn.edu.engzone.service.LessonService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LessonController {
    LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<LessonResponse>> getAll() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @GetMapping("/type/{type}/level/{level}")
    public ResponseEntity<List<LessonResponse>> getLessonsByTypeAndLevel(
            @PathVariable String type,
            @PathVariable String level) {
        try {
            LessonType lessonType = LessonType.valueOf(type.toUpperCase());
            Level lessonLevel = Level.valueOf(level.toUpperCase());
            return ResponseEntity.ok(lessonService.getLessonsByTypeAndLevel(lessonType, lessonLevel));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Type or Level invalid: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<List<QuizQuestionResponse>> getLessonQuestions(@PathVariable String id) {
        LessonResponse lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(lesson.getQuestions());
    }
}
