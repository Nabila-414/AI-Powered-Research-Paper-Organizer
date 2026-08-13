package com.mycompany.ai;

public class SummaryGenerator {

    public String summarize(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "No text provided to summarize.";
        }

        String[] sentences = text.split("(?<=[.!?])\\s+"); // split after . ! ?
        StringBuilder summary = new StringBuilder();
        int limit = Math.min(3, sentences.length); // take first 3 sentences

        for (int i = 0; i < limit; i++) {
            summary.append(sentences[i].trim()).append(" ");
        }

        return summary.toString().trim();
    }
}