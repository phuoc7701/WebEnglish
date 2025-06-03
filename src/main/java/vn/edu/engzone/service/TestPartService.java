package vn.edu.engzone.service;

import vn.edu.engzone.dto.request.TestPartRequest;
import vn.edu.engzone.entity.Question;
import vn.edu.engzone.entity.Test;
import vn.edu.engzone.entity.TestPart;
import vn.edu.engzone.mapper.TestPartMapper;
import vn.edu.engzone.repository.TestPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.engzone.repository.TestRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestPartService {

    @Autowired
    private TestPartRepository testPartRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestPartMapper testPartMapper;

    public List<TestPart> getPartsByTest(String testId) {
        return testPartRepository.findByTestId(testId);
    }

    public Optional<TestPart> getPartById(String id) {
        return testPartRepository.findById(id);
    }

    public TestPart createPart(TestPartRequest request) {
        TestPart entity = testPartMapper.toEntity(request);
        // Chuyển String testId sang UUID và tìm Test entity
        Test test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new RuntimeException("Test not found"));
        entity.setTest(test);
        return testPartRepository.save(entity);
    }
    public Optional<TestPart> updatePart(String id, TestPart updatedPart) {
        return testPartRepository.findById(id).map(part -> {
            part.setPartTitle(updatedPart.getPartTitle());
            part.setPartNumber(updatedPart.getPartNumber());

            // Cập nhật danh sách câu hỏi mà KHÔNG set list mới
            part.getQuestions().clear();
            if (updatedPart.getQuestions() != null) {
                for (Question q : updatedPart.getQuestions()) {
                    q.setPart(part); // set lại quan hệ cha
                    part.getQuestions().add(q);
                }
            }

            return testPartRepository.save(part);
        });
    }

    public boolean deletePart(String id) {
        if (testPartRepository.existsById(id)) {
            testPartRepository.deleteById(id);
            return true;
        }
        return false;
    }
}