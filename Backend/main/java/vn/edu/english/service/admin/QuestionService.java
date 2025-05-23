package vn.edu.english.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.english.dto.request.QuestionRequest;
import vn.edu.english.entity.Question;
import vn.edu.english.repository.QuestionRepository;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(()->new RuntimeException("Câu hỏi không tồn tài"));
    }

    public Question createQuestion(QuestionRequest request) {
        Question question = new Question();
        if (questionRepository.existsByContent(request.getContent())) {
            throw new RuntimeException("Câu hỏi này đã tồn tại.");
        }
        question.setContent(request.getContent());
        question.setAnswer(request.getAnswer());
        question.setQuestionType(request.getQuestionType());
        question.setCreatedAt(java.time.LocalDateTime.now());
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long id, QuestionRequest request){
        Question question = getQuestionById(id);
        question.setContent(request.getContent());
        question.setAnswer(request.getAnswer());
        question.setQuestionType(request.getQuestionType());


        return questionRepository.save(question);
    }

    public boolean deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
