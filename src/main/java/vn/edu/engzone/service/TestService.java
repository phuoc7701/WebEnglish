package vn.edu.engzone.service;


import vn.edu.engzone.dto.request.QuestionRequest;
import vn.edu.engzone.dto.request.TestPartRequest;
import vn.edu.engzone.dto.request.TestRequest;
import vn.edu.engzone.dto.response.QuestionResponse;
import vn.edu.engzone.dto.response.TestPartResponse;
import vn.edu.engzone.dto.response.TestResponse;
import vn.edu.engzone.entity.*;
import vn.edu.engzone.repository.TestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    public List<TestResponse> getAllTests() {
        return testRepository.findAll().stream().map(this::toTestResponse).collect(Collectors.toList());
    }

    public TestResponse getTest(String id) {
        return testRepository.findById(id).map(this::toTestResponse).orElse(null);
    }

    @Transactional
    public TestResponse createTest(TestRequest request) {

        Test test = new Test();
        test.setTitle(request.getTitle());
        test.setDescription(request.getDescription());
        test.setDuration(request.getDuration());


        List<TestPart> parts = request.getParts().stream().map(pr -> {
            TestPart part = new TestPart();
            part.setPartNumber(pr.getPartNumber());
            part.setPartTitle(pr.getPartTitle());
            part.setTest(test);

            List<Question> questions = pr.getQuestions().stream().map(qr -> {
                Question q = new Question();
                q.setQuestion(qr.getQuestion());
                q.setOptions(qr.getOptions());
                q.setCorrectAnswer(qr.getCorrectAnswer());
                q.setPart(part);
                return q;
            }).collect(Collectors.toList());

            part.setQuestions(questions);
            return part;
        }).collect(Collectors.toList());

        test.setParts(parts);
        Test savedTest = testRepository.save(test);
        return toTestResponse(savedTest);
    }

    @Transactional
    public TestResponse updateTest(String id, TestRequest request) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        test.setTitle(request.getTitle());
        test.setDescription(request.getDescription());
        test.setDuration(request.getDuration());

        // Map existing parts by id
        Map<String, TestPart> existingParts = test.getParts().stream()
                .collect(Collectors.toMap(p -> p.getId().toString(), p -> p));

        List<TestPart> updatedParts = new ArrayList<>();
        for (TestPartRequest pr : request.getParts()) {
            TestPart part;
            if (pr.getId() != null && existingParts.containsKey(pr.getId())) {
                part = existingParts.remove(pr.getId());
                part.setPartNumber(pr.getPartNumber());
                part.setPartTitle(pr.getPartTitle());
            } else {
                part = new TestPart();
                part.setTest(test);
                part.setPartNumber(pr.getPartNumber());
                part.setPartTitle(pr.getPartTitle());
            }

            // Handle questions
            Map<String, Question> existingQuestions = part.getQuestions() == null ? new HashMap<>()
                    : part.getQuestions().stream()
                    .filter(q -> q.getId() != null)
                    .collect(Collectors.toMap(q -> q.getId().toString(), q -> q));
            List<Question> updatedQuestions = new ArrayList<>();
            for (QuestionRequest qr : pr.getQuestions()) {
                Question q;
                if (qr.getId() != null && existingQuestions.containsKey(qr.getId())) {
                    q = existingQuestions.remove(qr.getId());
                    q.setQuestion(qr.getQuestion());
                    q.setOptions(qr.getOptions());
                    q.setCorrectAnswer(qr.getCorrectAnswer());
                } else {
                    q = new Question();
                    q.setPart(part);
                    q.setQuestion(qr.getQuestion());
                    q.setOptions(qr.getOptions());
                    q.setCorrectAnswer(qr.getCorrectAnswer());
                }
                updatedQuestions.add(q);
            }
            if (part.getQuestions() != null) {
                part.getQuestions().removeAll(existingQuestions.values());
            }
            if (part.getQuestions() == null) {
                part.setQuestions(new ArrayList<>());
            }
            part.getQuestions().clear();
            part.getQuestions().addAll(updatedQuestions);
            updatedParts.add(part);
        }
        test.getParts().removeAll(existingParts.values());
        test.getParts().clear();
        test.getParts().addAll(updatedParts);

        testRepository.save(test);
        return toTestResponse(test);
    }
    @Transactional
    public boolean deleteTest(String id) {
        Optional<Test> opt = testRepository.findById(id);
        if (opt.isPresent()) {
            testRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Mapping
    private TestResponse toTestResponse(Test test) {
        TestResponse res = new TestResponse();
        res.setId(test.getId());
        res.setTitle(test.getTitle());
        res.setDescription(test.getDescription());
        res.setDuration(test.getDuration());
//        res.setCourseId(test.getCourse() != null ? test.getCourse().getId() : null);
        res.setParts(
                Optional.ofNullable(test.getParts())
                        .orElse(List.of())
                        .stream()
                        .map(this::toTestPartResponse)
                        .collect(Collectors.toList())
        );
        return res;
    }

    private TestPartResponse toTestPartResponse(TestPart part) {
        TestPartResponse res = new TestPartResponse();
        res.setId(part.getId());
        res.setPartNumber(part.getPartNumber());
        res.setPartTitle(part.getPartTitle());
        res.setQuestions(part.getQuestions() != null
                ? part.getQuestions().stream().map(this::toQuestionResponse).collect(Collectors.toList())
                : null);
        return res;
    }

    private QuestionResponse toQuestionResponse(Question question) {
        QuestionResponse res = new QuestionResponse();
        res.setId(question.getId());
        res.setQuestion(question.getQuestion());
        res.setOptions(question.getOptions());
        res.setCorrectAnswer(question.getCorrectAnswer());
        return res;
    }
}