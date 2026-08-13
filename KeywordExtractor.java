package com.mycompany.ai;

import java.util.*;

public class KeywordExtractor {

    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
        "the", "is", "at", "which", "on", "a", "an", "and", "or", "but",
        "in", "of", "to", "for", "with", "as", "by", "this", "that", "it",
        "are", "was", "were", "be", "been", "has", "have", "had", "we",
        "our", "their", "from", "into", "these", "those", "can", "will"
    ));

    public String[] extract(String text, int count) {
        if (text == null || text.trim().isEmpty()) {
            return new String[0];
        }

        String[] words = text.toLowerCase().replaceAll("[^a-zA-Z\\s]", "").split("\\s+");

        Map<String, Integer> frequency = new HashMap<>();
        for (String word : words) {
            if (word.length() < 3 || STOPWORDS.contains(word)) {
                continue;
            }
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(frequency.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        int limit = Math.min(count, sorted.size());
        String[] keywords = new String[limit];
        for (int i = 0; i < limit; i++) {
            keywords[i] = sorted.get(i).getKey();
        }
        return keywords;
    }
}