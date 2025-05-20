package vn.edu.engzone.service;

import vn.edu.engzone.dto.response.AIResponse;
import vn.edu.engzone.service.impl.WritingAIServiceImpl;

public interface WritingAIService {
    String generateEssayTopicAI();

    static AIResponse evaluateEssayAI(String topic, String writing) {
        return WritingAIServiceImpl.evaluateEssayAI(topic, writing);
    };
}

