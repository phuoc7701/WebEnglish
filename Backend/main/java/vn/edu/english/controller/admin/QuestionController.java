package vn.edu.english.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.english.dto.request.QuestionRequest;
import vn.edu.english.entity.Question;
import vn.edu.english.service.admin.QuestionService;

import java.util.List;

@RestController
@RequestMapping("admin/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<Question> getAllQuestion() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    Question getQuestion(@PathVariable("id") Long questionId){
        return questionService.getQuestionById(questionId);
    }

    @PostMapping
    public Question createQuestion(@RequestBody QuestionRequest question) {
        return questionService.createQuestion(question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequest question) {
        Question updated = questionService.updateQuestion(id, question);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable("id") Long questionId){
        questionService.deleteQuestion(questionId);
        return "Đã xóa câu hỏi thành công";
    }
}
