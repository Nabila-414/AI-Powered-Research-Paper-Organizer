package com.mycompany.gui;

import javax.swing.*;
import java.awt.*;

public class AIAssistantDashboard extends JFrame {

    private static final Color SIDEBAR_BG = new Color(12, 68, 124);
    private static final Color SIDEBAR_TEXT = new Color(230, 241, 251);

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private Runnable onProfileClick = () ->
        JOptionPane.showMessageDialog(this,
            "Profile section coming soon (built by another team member).",
            "Profile", JOptionPane.INFORMATION_MESSAGE);

    private static final String WELCOME_CARD = "WELCOME";
    private static final String SUMMARY_CARD = "SUMMARY";
    private static final String CITATION_CARD = "CITATION";
    private static final String KEYWORD_CARD = "KEYWORD";
    private static final String RECOMMEND_CARD = "RECOMMEND";

    public AIAssistantDashboard() {
        setTitle("AI Assistant - Research Paper Organizer");
        setSize(850, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildSidebar(), BorderLayout.WEST);
        add(buildContentArea(), BorderLayout.CENTER);
    }

    public void setOnProfileClick(Runnable action) {
        this.onProfileClick = action;
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(230, 240, 250));
        header.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JLabel titleLabel = new JLabel("AI Assistant — Research Paper Organizer");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        titleLabel.setForeground(SIDEBAR_BG);
        header.add(titleLabel, BorderLayout.WEST);

        JButton avatarButton = new JButton("M4");
        avatarButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        avatarButton.setForeground(Color.WHITE);
        avatarButton.setBackground(SIDEBAR_BG);
        avatarButton.setFocusPainted(false);
        avatarButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        avatarButton.setToolTipText("Profile");
        avatarButton.addActionListener(e -> onProfileClick.run());

        header.add(avatarButton, BorderLayout.EAST);
        return header;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        sidebar.setPreferredSize(new Dimension(190, 0));
        sidebar.setBackground(SIDEBAR_BG);

        JLabel heading = new JLabel("AI FEATURES");
        heading.setFont(new Font("SansSerif", Font.BOLD, 12));
        heading.setForeground(new Color(181, 212, 244));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(heading);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton summaryBtn = createNavButton("AI Summary");
        JButton citationBtn = createNavButton("Citation Generator");
        JButton keywordBtn = createNavButton("Keyword Extraction");
        JButton recommendBtn = createNavButton("Recommendations");

        for (JButton b : new JButton[]{summaryBtn, citationBtn, keywordBtn, recommendBtn}) {
            sidebar.add(b);
            sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        summaryBtn.addActionListener(e -> cardLayout.show(contentPanel, SUMMARY_CARD));
        citationBtn.addActionListener(e -> cardLayout.show(contentPanel, CITATION_CARD));
        keywordBtn.addActionListener(e -> cardLayout.show(contentPanel, KEYWORD_CARD));
        recommendBtn.addActionListener(e -> cardLayout.show(contentPanel, RECOMMEND_CARD));

        return sidebar;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(170, 36));
        button.setBackground(SIDEBAR_BG);
        button.setForeground(SIDEBAR_TEXT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        return button;
    }

    private JPanel buildContentArea() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        JPanel welcomePanel = new JPanel(new GridBagLayout());
        JLabel welcomeLabel = new JLabel("Select a feature from the left to get started.");
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        welcomePanel.add(welcomeLabel);

        contentPanel.add(welcomePanel, WELCOME_CARD);
        contentPanel.add(new AISummaryPanel(), SUMMARY_CARD);
        contentPanel.add(new AICitationGeneratorPanel(), CITATION_CARD);
        contentPanel.add(new AIKeywordExtractionPanel(), KEYWORD_CARD);
        contentPanel.add(new AIRecommendationPanel(), RECOMMEND_CARD);

        cardLayout.show(contentPanel, WELCOME_CARD);
        return contentPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AIAssistantDashboard().setVisible(true));
    }
}