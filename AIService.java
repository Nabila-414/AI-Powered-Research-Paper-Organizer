package com.mycompany.ai;

import java.util.List;

public class AIService {
    private SummaryGenerator summaryGenerator;
    private KeywordExtractor keywordExtractor;
    private CitationGenerator citationGenerator;
    private RecommendationEngine recommendationEngine;

    public AIService() {
        this.summaryGenerator = new SummaryGenerator();
        this.keywordExtractor = new KeywordExtractor();
        this.citationGenerator = new CitationGenerator();
        this.recommendationEngine = new RecommendationEngine();
    }

    public String generateSummary(String paperText) {
        return summaryGenerator.summarize(paperText);
    }

    public String[] extractKeywords(String paperText, int count) {
        return keywordExtractor.extract(paperText, count);
    }

    public String generateCitation(Paper paper, String style) {
        return citationGenerator.generate(paper, style);
    }

    public List<RecommendationEngine.Recommendation> recommendPapers(Paper currentPaper, List<Paper> allPapers) {
        return recommendationEngine.recommend(currentPaper, allPapers);
    }
}