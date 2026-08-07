package manager;

import model.Paper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * FavoriteManager.java
 * ---------------------
 * Handles Bookmark/Favorite papers and the Recent Papers list.
 * Part of Member 3 - Search & Organization module.
 */
public class FavoriteManager {

    private List<Paper> allPapers;
    private static final int MAX_RECENT = 10; // how many recent papers to remember

    public FavoriteManager(List<Paper> allPapers) {
        this.allPapers = allPapers;
    }

    public void setAllPapers(List<Paper> allPapers) {
        this.allPapers = allPapers;
    }

    /** Toggle bookmark on/off for a given paper. */
    public void toggleFavorite(Paper paper) {
        paper.setFavorite(!paper.isFavorite());
    }

    public void addFavorite(Paper paper) {
        paper.setFavorite(true);
    }

    public void removeFavorite(Paper paper) {
        paper.setFavorite(false);
    }

    /** Returns all papers the user has bookmarked. */
    public List<Paper> getFavorites() {
        List<Paper> favorites = new ArrayList<>();
        for (Paper p : allPapers) {
            if (p.isFavorite()) {
                favorites.add(p);
            }
        }
        return favorites;
    }

    /** Call this whenever a paper is opened, so it shows up under "Recent Papers". */
    public void markAsOpened(Paper paper) {
        paper.markOpened();
    }

    /**
     * Returns the most recently opened papers, newest first, capped at MAX_RECENT.
     */
    public List<Paper> getRecentPapers() {
        List<Paper> recent = new ArrayList<>();
        for (Paper p : allPapers) {
            if (p.getLastOpened() != null) {
                recent.add(p);
            }
        }
        recent.sort(new Comparator<Paper>() {
            @Override
            public int compare(Paper a, Paper b) {
                LocalDate da = a.getLastOpened();
                LocalDate db = b.getLastOpened();
                return db.compareTo(da); // newest first
            }
        });
        if (recent.size() > MAX_RECENT) {
            return recent.subList(0, MAX_RECENT);
        }
        return recent;
    }
}
