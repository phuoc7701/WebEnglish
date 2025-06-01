package vn.edu.engzone.prompt;

public class GeminiPrompt {
    public static String generateEssayTopicPrompt() {
        return """
                You are a TOEIC writing experienced expert. Generate one TOEIC Writing Part 3 (Write an Opinion Essay – Task 8) question.
                The topic should vary across communication, lifestyle, education, workplace, technology, or social issues.
                Suitable for intermediate to upper-intermediate learners. Format it clearly as an official TOEIC prompt,
                and ensure it's designed to be answered within 30 minutes. Use natural English spacing between words and
                proper punctuation. Only return the question itself, without any additional text, instructions, or formatting.
                Example: Some people believe that companies should prioritize employee well-being programs, such as mental health
                support and flexible work arrangements, even if it means reduced short-term profits. Others argue that a company's
                primary responsibility is to maximize profits for shareholders. Which view do you agree with? Explain your reasons.
            """;
    }

    public static String generateEssayAnswerPrompt(String topic, String writing) {
        return """
               You are an experienced evaluator for TOEIC Writing Part 3 (Write an Opinion Essay – Task 8).
               In this part, the test taker is expected to write a minimum 300-word opinion essay that clearly states,
               explains, and supports their opinion on a given issue.
                
               Here is the prompt and the essay to evaluate: Topic: %s Essay: %s
                
               Please return your evaluation in the following JSON format: 
               { 
                    "score": 0-30,
                    "formatted_html": "<html with corrections and highlights>",
                    "review_comment_vi": "Brief comment in Vietnamese, pointing out strengths and areas for improvement."
                } 
                
                Correction instructions: 
               - Highlight incorrect grammar, vocabulary, or structure using: <span style="color:#f00;background-color:#ffe6e1;text-decoration:line-through">
               - Provide the corrected version beside it using: <span style="color:#008000;background-color:#e6ffe6;margin-left:2px"> - Do not include explanations or comments in the HTML.
               - Do not return anything except the formatted JSON.
                """.formatted(topic, writing);
    }
}
