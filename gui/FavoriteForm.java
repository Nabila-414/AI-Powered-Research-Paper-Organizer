package gui;

import manager.FavoriteManager;
import model.Paper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * FavoriteForm.java
 * ------------------
 * GUI Panel: shows two lists side by side -
 *   1) Bookmarked / Favorite papers
 *   2) Recently opened papers
 * Part of Member 3 - Search & Organization module.
 */
public class FavoriteForm extends JPanel {

    private final FavoriteManager favoriteManager;

    private DefaultTableModel favoriteTableModel;
    private JTable favoriteTable;
    private DefaultTableModel recentTableModel;
    private JTable recentTable;

    private List<Paper> currentFavorites;

    public FavoriteForm(FavoriteManager favoriteManager) {
        this.favoriteManager = favoriteManager;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Favorites & Recent Papers");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 2, 15, 0));
        content.setBackground(Color.WHITE);
        content.add(buildFavoritePanel());
        content.add(buildRecentPanel());
        add(content, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refresh());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.WHITE);
        bottom.add(refreshBtn);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    private JPanel buildFavoritePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("\u2605 Bookmarked Papers"));

        String[] cols = {"Title", "Author", "Year"};
        favoriteTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        favoriteTable = new JTable(favoriteTableModel);
        favoriteTable.setRowHeight(24);
        panel.add(new JScrollPane(favoriteTable), BorderLayout.CENTER);

        JButton removeBtn = new JButton("Remove from Favorites");
        removeBtn.addActionListener(e -> removeSelectedFavorite());
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.setBackground(Color.WHITE);
        btnRow.add(removeBtn);
        panel.add(btnRow, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildRecentPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("\u23F1 Recently Opened Papers"));

        String[] cols = {"Title", "Author", "Last Opened"};
        recentTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        recentTable = new JTable(recentTableModel);
        recentTable.setRowHeight(24);
        panel.add(new JScrollPane(recentTable), BorderLayout.CENTER);

        return panel;
    }

    /** Reloads both tables from FavoriteManager. Call this after switching tabs, or after opening/bookmarking a paper. */
    public void refresh() {
        currentFavorites = favoriteManager.getFavorites();
        favoriteTableModel.setRowCount(0);
        for (Paper p : currentFavorites) {
            favoriteTableModel.addRow(new Object[]{p.getTitle(), p.getAuthor(), p.getYear()});
        }

        List<Paper> recent = favoriteManager.getRecentPapers();
        recentTableModel.setRowCount(0);
        for (Paper p : recent) {
            recentTableModel.addRow(new Object[]{p.getTitle(), p.getAuthor(), p.getLastOpened()});
        }
    }

    private void removeSelectedFavorite() {
        int row = favoriteTable.getSelectedRow();
        if (row < 0 || currentFavorites == null || row >= currentFavorites.size()) {
            JOptionPane.showMessageDialog(this, "Please select a paper first.");
            return;
        }
        Paper selected = currentFavorites.get(row);
        favoriteManager.removeFavorite(selected);
        refresh();
    }
}
