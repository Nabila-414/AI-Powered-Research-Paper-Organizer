package com.mycompany.ai;

import java.util.*;

public class RecommendationEngine {

    private KeywordExtractor keywordExtractor = new KeywordExtractor();

    public List<Recommendation> recommend(Paper currentPaper, List<Paper> allPapers) {
        List<Recommendation> results = new ArrayList<>();
        if (currentPaper == null || allPapers == null) {
            return results;
        }

        Set<String> currentWords = new HashSet<>(
            Arrays.asList(keywordExtractor.extract(currentPaper.getContent(), 15))
        );

        for (Paper other : allPapers) {
            if (other == currentPaper) continue;

            Set<String> otherWords = new HashSet<>(
                Arrays.asList(keywordExtractor.extract(other.getContent(), 15))
            );

            int sharedWords = 0;
            for (String word : currentWords) {
                if (otherWords.contains(word)) sharedWords++;
            }

            int totalWords = Math.max(currentWords.size(), 1);
            int matchPercent = (int) ((sharedWords / (double) totalWords) * 100);

            results.add(new Recommendation(other, matchPercent));
        }

        results.sort((a, b) -> b.matchPercent - a.matchPercent);
        return results;
    }

    public static class Recommendation {
        public final Paper paper;
        public final int matchPercent;

        public Recommendation(Paper paper, int matchPercent) {
            this.paper = paper;
            this.matchPercent = matchPercent;
        }
    }
}