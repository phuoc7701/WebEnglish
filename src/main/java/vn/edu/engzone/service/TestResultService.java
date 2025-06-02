package vn.edu.engzone.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.edu.engzone.entity.Question;
import vn.edu.engzone.entity.Test;
import vn.edu.engzone.entity.TestPart;
import vn.edu.engzone.entity.TestResult;
import vn.edu.engzone.repository.TestRepository;
import vn.edu.engzone.repository.TestResultRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class TestResultService {

    private static final Logger logger = LoggerFactory.getLogger(TestResultService.class);

    private final TestRepository testRepository;
    private final TestResultRepository testResultRepository;

    public int evaluateTest(String testId, Map<String, String> userAnswers, String username) {
        Optional<Test> optionalTest = testRepository.findById(testId);
        if (optionalTest.isEmpty()) {
            throw new RuntimeException("Test not found with ID: " + testId);
        }
        int score = 0;
        Test test = optionalTest.get();
        for (TestPart part : test.getParts()) {
            for (Question q : part.getQuestions()) {
                String questionId = q.getId();
                String userAnswer = userAnswers.get(questionId);
                String correctAnswer = q.getCorrectAnswer();

                logger.info("Checking questionId: " + questionId + ", userAnswer: " + userAnswer + ", correctAnswer: " + correctAnswer);

                if (userAnswer != null && userAnswer.trim().equalsIgnoreCase(correctAnswer.trim())) {
                    score++;

                } else {

                }
            }
        }

        TestResult result = TestResult.builder()
                .testId(testId)
                .username(username)
                .score(score)
                .submitTime(LocalDateTime.now())
                .build();
        testResultRepository.save(result);

//        logger.info("Checking questionId: " + questionId + ", userAnswer: " + userAnswer + ", correctAnswer: " + correctAnswer);

        return score;
    }
}