package manager;

import model.Paper;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchManager.java
 * -------------------
 * Handles all searching and filtering logic for research papers.
 * This is the "brain" of Member 3's module - the GUI (SearchForm) only
 * calls these methods and displays whatever list comes back.
 */
public class SearchManager {

    private List<Paper> allPapers; // the full paper list (normally comes from PaperManager / database)

    public SearchManager(List<Paper> allPapers) {
        this.allPapers = allPapers;
    }

    /** Allows refreshing the source list (e.g. after Member 2 adds/deletes a paper). */
    public void setAllPapers(List<Paper> allPapers) {
        this.allPapers = allPapers;
    }

    /**
     * Search by keyword. Matches against title OR author (case-insensitive).
     */
    public List<Paper> searchByKeyword(String keyword) {
        List<Paper> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(allPapers);
        }
        String lowerKeyword = keyword.toLowerCase().trim();
        for (Paper p : allPapers) {
            if (p.getTitle().toLowerCase().contains(lowerKeyword)
                    || p.getAuthor().toLowerCase().contains(lowerKeyword)) {
                result.add(p);
            }
        }
        return result;
    }

    /** Filter by author name (partial match, case-insensitive). */
    public List<Paper> filterByAuthor(String author) {
        List<Paper> result = new ArrayList<>();
        if (author == null || author.trim().isEmpty()) {
            return new ArrayList<>(allPapers);
        }
        String lowerAuthor = author.toLowerCase().trim();
        for (Paper p : allPapers) {
            if (p.getAuthor().toLowerCase().contains(lowerAuthor)) {
                result.add(p);
            }
        }
        return result;
    }

    /** Filter by exact publication year. Pass -1 (or any negative number) to skip this filter. */
    public List<Paper> filterByYear(int year) {
        List<Paper> result = new ArrayList<>();
        if (year <= 0) {
            return new ArrayList<>(allPapers);
        }
        for (Paper p : allPapers) {
            if (p.getYear() == year) {
                result.add(p);
            }
        }
        return result;
    }

    /** Filter by category name. Pass null or "All" to skip this filter. */
    public List<Paper> filterByCategory(String category) {
        List<Paper> result = new ArrayList<>();
        if (category == null || category.equalsIgnoreCase("All") || category.trim().isEmpty()) {
            return new ArrayList<>(allPapers);
        }
        for (Paper p : allPapers) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                result.add(p);
            }
        }
        return result;
    }

    /**
     * Combined / advanced search: keyword + author + year + category together.
     * Any parameter can be left empty/blank/<=0 to mean "don't filter by this field".
     */
    public List<Paper> advancedSearch(String keyword, String author, int year, String category) {
        List<Paper> result = new ArrayList<>();
        String lowerKeyword = (keyword == null) ? "" : keyword.toLowerCase().trim();
        String lowerAuthor = (author == null) ? "" : author.toLowerCase().trim();

        for (Paper p : allPapers) {
            boolean matchesKeyword = lowerKeyword.isEmpty()
                    || p.getTitle().toLowerCase().contains(lowerKeyword);

            boolean matchesAuthor = lowerAuthor.isEmpty()
                    || p.getAuthor().toLowerCase().contains(lowerAuthor);

            boolean matchesYear = (year <= 0) || (p.getYear() == year);

            boolean matchesCategory = (category == null)
                    || category.equalsIgnoreCase("All")
                    || category.trim().isEmpty()
                    || p.getCategory().equalsIgnoreCase(category);

            if (matchesKeyword && matchesAuthor && matchesYear && matchesCategory) {
                result.add(p);
            }
        }
        return result;
    }
}
