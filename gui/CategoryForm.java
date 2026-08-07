package gui;

import manager.SearchManager;
import model.Category;
import model.Paper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * CategoryForm.java
 * ------------------
 * GUI Panel: shows the list of categories on the left, and papers
 * belonging to the selected category on the right. Also allows adding
 * a new category (in-memory only, for this module's demo).
 * Part of Member 3 - Search & Organization module.
 */
public class CategoryForm extends JPanel {

    private final List<Category> categories;
    private final SearchManager searchManager;

    private DefaultListModel<Category> categoryListModel;
    private JList<Category> categoryList;
    private DefaultListModel<String> paperListModel;
    private JList<String> paperList;
    private JLabel countLabel;

    public CategoryForm(List<Category> categories, SearchManager searchManager) {
        this.categories = categories;
        this.searchManager = searchManager;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Categories");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        add(buildSplitPanel(), BorderLayout.CENTER);
    }

    private JSplitPane buildSplitPanel() {
        // ---- LEFT: category list + add button ----
        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.setBackground(Color.WHITE);

        categoryListModel = new DefaultListModel<>();
        for (Category c : categories) {
            categoryListModel.addElement(c);
        }
        categoryList = new JList<>(categoryListModel);
        categoryList.setFont(new Font("SansSerif", Font.PLAIN, 15));
        categoryList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        left.add(new JScrollPane(categoryList), BorderLayout.CENTER);

        JPanel addPanel = new JPanel(new BorderLayout(5, 5));
        addPanel.setBackground(Color.WHITE);
        JTextField newCategoryField = new JTextField();
        JButton addBtn = new JButton("Add Category");
        addPanel.add(newCategoryField, BorderLayout.CENTER);
        addPanel.add(addBtn, BorderLayout.EAST);
        left.add(addPanel, BorderLayout.SOUTH);

        left.setPreferredSize(new Dimension(300, 0));

        // ---- RIGHT: papers under selected category ----
        JPanel right = new JPanel(new BorderLayout(5, 5));
        right.setBackground(Color.WHITE);

        JLabel rightTitle = new JLabel("Papers in this category");
        rightTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        right.add(rightTitle, BorderLayout.NORTH);

        paperListModel = new DefaultListModel<>();
        paperList = new JList<>(paperListModel);
        paperList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        right.add(new JScrollPane(paperList), BorderLayout.CENTER);

        countLabel = new JLabel("Select a category on the left");
        right.add(countLabel, BorderLayout.SOUTH);

        // ---- Actions ----
        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showPapersForSelectedCategory();
            }
        });

        addBtn.addActionListener(e -> {
            String name = newCategoryField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please type a category name first.");
                return;
            }
            Category newCat = new Category(name, "");
            if (categories.contains(newCat)) {
                JOptionPane.showMessageDialog(this, "That category already exists.");
                return;
            }
            categories.add(newCat);
            categoryListModel.addElement(newCat);
            newCategoryField.setText("");
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setDividerLocation(300);
        split.setBorder(null);
        return split;
    }

    private void showPapersForSelectedCategory() {
        Category selected = categoryList.getSelectedValue();
        paperListModel.clear();
        if (selected == null) {
            countLabel.setText("Select a category on the left");
            return;
        }
        List<Paper> papers = searchManager.filterByCategory(selected.getName());
        for (Paper p : papers) {
            paperListModel.addElement(p.getTitle() + "  \u2014  " + p.getAuthor() + " (" + p.getYear() + ")");
        }
        countLabel.setText(papers.size() + " paper(s) in \"" + selected.getName() + "\"");
    }
}
