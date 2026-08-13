package com.mycompany.gui;

import com.mycompany.ai.Paper;
import com.mycompany.ai.RecommendationEngine;
import com.mycompany.ai.RecommendationEngine.Recommendation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class AIRecommendationPanel extends JPanel {

    private static final Color ACCENT = new Color(153, 53, 86);
    private static final Color BADGE_BG = new Color(251, 234, 240);
    private static final Color BADGE_TEXT = new Color(114, 36, 62);

    private JComboBox<Paper> paperSelector;
    private JPanel resultsPanel;
    private RecommendationEngine recommendationEngine;
    private List<Paper> allPapers;

    public AIRecommendationPanel() {
        recommendationEngine = new RecommendationEngine();
        allPapers = createSampleData();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("AI Paper Recommendation");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel selectRow = new JPanel(new BorderLayout(5, 5));
        selectRow.add(new JLabel("Select current paper:"), BorderLayout.NORTH);
        paperSelector = new JComboBox<>(allPapers.toArray(new Paper[0]));
        selectRow.add(paperSelector, BorderLayout.CENTER);
        topPanel.add(selectRow, BorderLayout.CENTER);

        JButton recommendButton = new JButton("Get Recommendations");
        recommendButton.setBackground(ACCENT);
        recommendButton.setForeground(Color.WHITE);
        recommendButton.addActionListener(this::onRecommendClicked);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(recommendButton);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    private void onRecommendClicked(ActionEvent e) {
        Paper selected = (Paper) paperSelector.getSelectedItem();
        if (selected == null) return;

        List<Recommendation> results = recommendationEngine.recommend(selected, allPapers);

        resultsPanel.removeAll();
        for (Recommendation rec : results) {
            resultsPanel.add(buildRecommendationCard(rec));
            resultsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private JPanel buildRecommendationCard(Recommendation rec) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel(rec.paper.getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        JLabel metaLabel = new JLabel(rec.paper.getAuthor() + ", " + rec.paper.getYear()
            + " — " + rec.paper.getJournal());
        metaLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        metaLabel.setForeground(Color.GRAY);
        textPanel.add(titleLabel);
        textPanel.add(metaLabel);
        card.add(textPanel, BorderLayout.WEST);

        JLabel matchBadge = new JLabel(rec.matchPercent + "% match");
        matchBadge.setOpaque(true);
        matchBadge.setBackground(BADGE_BG);
        matchBadge.setForeground(BADGE_TEXT);
        matchBadge.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        matchBadge.setFont(new Font("SansSerif", Font.BOLD, 12));
        card.add(matchBadge, BorderLayout.EAST);

        return card;
    }

    private List<Paper> createSampleData() {
        List<Paper> papers = new ArrayList<>();
        papers.add(new Paper("Deep Learning for NLP", "J. Smith", "2023", "IEEE",
            "deep learning neural network natural language processing model training"));
        papers.add(new Paper("Transformer Models in NLP", "A. Karim", "2022", "IEEE Access",
            "transformer attention mechanism neural network language model"));
        papers.add(new Paper("Attention Mechanisms Survey", "R. Chowdhury", "2021", "ACM",
            "attention mechanism deep learning neural network survey"));
        papers.add(new Paper("Text Classification with BERT", "M. Hasan", "2023", "Springer",
            "text classification bert neural network language model training"));
        return papers;
    }
}