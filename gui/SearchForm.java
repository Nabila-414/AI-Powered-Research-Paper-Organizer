package gui;

import manager.FavoriteManager;
import manager.SearchManager;
import model.Category;
import model.Paper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * SearchForm.java
 * ----------------
 * GUI Panel: lets the user search papers by keyword, and filter by
 * author, year, and category. Results show in a table.
 * Part of Member 3 - Search & Organization module.
 */
public class SearchForm extends JPanel {

    private final SearchManager searchManager;
    private final FavoriteManager favoriteManager;
    private final List<Category> categories;

    private JTextField keywordField;
    private JTextField authorField;
    private JTextField yearField;
    private JComboBox<String> categoryBox;

    private DefaultTableModel tableModel;
    private JTable resultTable;
    private JLabel resultCountLabel;

    public SearchForm(SearchManager searchManager, FavoriteManager favoriteManager, List<Category> categories) {
        this.searchManager = searchManager;
        this.favoriteManager = favoriteManager;
        this.categories = categories;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        add(buildFilterPanel(), BorderLayout.NORTH);
        add(buildResultPanel(), BorderLayout.CENTER);

        // show everything by default when the form loads
        runSearch();
    }

    /** Top panel: keyword box + author/year/category filters + buttons */
    private JPanel buildFilterPanel() {
        JPanel outer = new JPanel();
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
        outer.setBackground(Color.WHITE);

        JLabel title = new JLabel("Search Papers");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        outer.add(title);
        outer.add(Box.createVerticalStrut(10));

        // Row 1: keyword search
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        row1.setBackground(Color.WHITE);
        keywordField = new JTextField(30);
        JButton searchBtn = new JButton("Search");
        JButton clearBtn = new JButton("Clear Filters");
        row1.add(new JLabel("Keyword (title/author):"));
        row1.add(keywordField);
        row1.add(searchBtn);
        row1.add(clearBtn);
        row1.setAlignmentX(Component.LEFT_ALIGNMENT);
        outer.add(row1);

        // Row 2: advanced filters
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        row2.setBackground(Color.WHITE);

        authorField = new JTextField(14);
        yearField = new JTextField(6);

        categoryBox = new JComboBox<>();
        categoryBox.addItem("All");
        for (Category c : categories) {
            categoryBox.addItem(c.getName());
        }

        JButton filterBtn = new JButton("Apply Filters");

        row2.add(new JLabel("Author:"));
        row2.add(authorField);
        row2.add(new JLabel("Year:"));
        row2.add(yearField);
        row2.add(new JLabel("Category:"));
        row2.add(categoryBox);
        row2.add(filterBtn);
        row2.setAlignmentX(Component.LEFT_ALIGNMENT);
        outer.add(row2);

        // ---- Actions ----
        searchBtn.addActionListener(e -> runSearch());
        keywordField.addActionListener(e -> runSearch()); // press Enter to search
        filterBtn.addActionListener(e -> runAdvancedSearch());
        clearBtn.addActionListener(e -> clearFilters());

        return outer;
    }

    /** Center panel: results table + bookmark/open buttons */
    private JPanel buildResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        String[] columns = {"Title", "Author", "Year", "Category", "Favorite"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only table
            }
        };
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(26);
        resultTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resultTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        resultTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBackground(Color.WHITE);

        resultCountLabel = new JLabel("0 papers found");
        bottomBar.add(resultCountLabel, BorderLayout.WEST);

        JPanel actionBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionBtns.setBackground(Color.WHITE);
        JButton toggleFavBtn = new JButton("Toggle Favorite \u2605");
        JButton openBtn = new JButton("Open PDF");
        actionBtns.add(toggleFavBtn);
        actionBtns.add(openBtn);
        bottomBar.add(actionBtns, BorderLayout.EAST);

        panel.add(bottomBar, BorderLayout.SOUTH);

        toggleFavBtn.addActionListener(e -> toggleSelectedFavorite());
        openBtn.addActionListener(e -> openSelectedPaper());

        return panel;
    }

    // ---------------- Logic ----------------

    private List<Paper> currentResults;

    private void runSearch() {
        String keyword = keywordField.getText();
        currentResults = searchManager.searchByKeyword(keyword);
        populateTable(currentResults);
    }

    private void runAdvancedSearch() {
        String keyword = keywordField.getText();
        String author = authorField.getText();
        String yearText = yearField.getText().trim();
        int year = -1;
        if (!yearText.isEmpty()) {
            try {
                year = Integer.parseInt(yearText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Year must be a number, e.g. 2023",
                        "Invalid Year", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        String category = (String) categoryBox.getSelectedItem();
        currentResults = searchManager.advancedSearch(keyword, author, year, category);
        populateTable(currentResults);
    }

    private void clearFilters() {
        keywordField.setText("");
        authorField.setText("");
        yearField.setText("");
        categoryBox.setSelectedIndex(0);
        runSearch();
    }

    private void populateTable(List<Paper> papers) {
        tableModel.setRowCount(0);
        for (Paper p : papers) {
            tableModel.addRow(new Object[]{
                    p.getTitle(), p.getAuthor(), p.getYear(), p.getCategory(),
                    p.isFavorite() ? "\u2605 Yes" : "No"
            });
        }
        resultCountLabel.setText(papers.size() + " paper(s) found");
    }

    private void toggleSelectedFavorite() {
        int row = resultTable.getSelectedRow();
        if (row < 0 || currentResults == null || row >= currentResults.size()) {
            JOptionPane.showMessageDialog(this, "Please select a paper first.");
            return;
        }
        Paper selected = currentResults.get(row);
        favoriteManager.toggleFavorite(selected);
        populateTable(currentResults); // refresh the Favorite column
    }

    private void openSelectedPaper() {
        int row = resultTable.getSelectedRow();
        if (row < 0 || currentResults == null || row >= currentResults.size()) {
            JOptionPane.showMessageDialog(this, "Please select a paper first.");
            return;
        }
        Paper selected = currentResults.get(row);
        favoriteManager.markAsOpened(selected);
        JOptionPane.showMessageDialog(this,
                "Opening: " + selected.getTitle() + "\nFile: " + selected.getFilePath()
                        + "\n\n(In the full project, this would launch \"Open PDF\" from Member 2's module.)",
                "Open PDF", JOptionPane.INFORMATION_MESSAGE);
    }
}
