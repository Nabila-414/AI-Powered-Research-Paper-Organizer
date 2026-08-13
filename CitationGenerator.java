package com.mycompany.ai;

public class CitationGenerator {

    public String generate(Paper paper, String style) {
        if (paper == null) {
            return "No paper selected.";
        }

        if (style.equalsIgnoreCase("APA")) {
            return paper.getAuthor() + " (" + paper.getYear() + "). "
                 + paper.getTitle() + ". " + paper.getJournal() + ".";
        } else if (style.equalsIgnoreCase("IEEE")) {
            return "[1] " + paper.getAuthor() + ", \"" + paper.getTitle()
                 + ",\" " + paper.getJournal() + ", " + paper.getYear() + ".";
        }

        return "Unsupported citation style: " + style;
    }
}