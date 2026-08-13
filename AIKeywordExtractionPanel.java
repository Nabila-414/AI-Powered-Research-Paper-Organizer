package com.mycompany.gui;

import com.mycompany.ai.KeywordExtractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AIKeywordExtractionPanel extends JPanel {

    private static final Color ACCENT = new Color(153, 60, 29);
    private static final Color CHIP_BG = new Color(250, 236, 231);
    private static final Color CHIP_TEXT = new Color(113, 43, 19);

    private JTextArea inputArea;
    private JSlider countSlider;
    private JPanel keywordChipPanel;
    private KeywordExtractor keywordExtractor;

    public AIKeywordExtractionPanel() {
        keywordExtractor = new KeywordExtractor();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("AI Keyword Extraction");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(new JLabel("Paste paper text below:"), BorderLayout.NORTH);
        inputArea = new JTextArea(6, 20);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        centerPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel controlsRow = new JPanel(new BorderLayout(10, 10));

        JPanel sliderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sliderPanel.add(new JLabel("Number of keywords:"));
        countSlider = new JSlider(3, 10, 5);
        countSlider.setPreferredSize(new Dimension(120, 25));
        JLabel countLabel = new JLabel("5");
        countSlider.addChangeListener(e -> countLabel.setText(String.valueOf(countSlider.getValue())));
        sliderPanel.add(countSlider);
        sliderPanel.add(countLabel);
        controlsRow.add(sliderPanel, BorderLayout.WEST);

        JButton extractButton = new JButton("Extract Keywords");
        extractButton.setBackground(ACCENT);
        extractButton.setForeground(Color.WHITE);
        extractButton.addActionListener(this::onExtractClicked);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(extractButton);
        controlsRow.add(btnPanel, BorderLayout.EAST);

        centerPanel.add(controlsRow, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        JPanel outputWrapper = new JPanel(new BorderLayout(5, 5));
        outputWrapper.add(new JLabel("Extracted keywords:"), BorderLayout.NORTH);
        keywordChipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        outputWrapper.add(new JScrollPane(keywordChipPanel), BorderLayout.CENTER);
        outputWrapper.setPreferredSize(new Dimension(0, 100));
        add(outputWrapper, BorderLayout.SOUTH);
    }

    private void onExtractClicked(ActionEvent e) {
        String text = inputArea.getText();
        if (text.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please paste some paper text first.",
                "No Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] keywords = keywordExtractor.extract(text, countSlider.getValue());

        keywordChipPanel.removeAll();
        for (String keyword : keywords) {
            JLabel chip = new JLabel(keyword);
            chip.setOpaque(true);
            chip.setBackground(CHIP_BG);
            chip.setForeground(CHIP_TEXT);
            chip.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
            keywordChipPanel.add(chip);
        }
        keywordChipPanel.revalidate();
        keywordChipPanel.repaint();
    }
}