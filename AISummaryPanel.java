package com.mycompany.gui;

import com.mycompany.ai.SummaryGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;

public class AISummaryPanel extends JPanel {

    private static final Color ACCENT = new Color(12, 68, 124);
    private static final Color OUTPUT_BG = new Color(238, 237, 254);
    private static final Color OUTPUT_TEXT = new Color(60, 52, 137);

    private JTextArea inputArea;
    private JTextArea outputArea;
    private SummaryGenerator summaryGenerator;

    public AISummaryPanel() {
        summaryGenerator = new SummaryGenerator();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("AI Summary Generator");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(new JLabel("Paste paper text below:"), BorderLayout.NORTH);
        inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        JButton uploadButton = new JButton("Upload paper (.txt)");
        uploadButton.addActionListener(this::onUploadClicked);
        JPanel uploadRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        uploadRow.add(uploadButton);
        inputPanel.add(uploadRow, BorderLayout.SOUTH);

        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputPanel.add(new JLabel("Generated summary:"), BorderLayout.NORTH);
        outputArea = new JTextArea();
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        outputArea.setBackground(OUTPUT_BG);
        outputArea.setForeground(OUTPUT_TEXT);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JButton copyButton = new JButton("Copy");
        copyButton.addActionListener(this::onCopyClicked);
        JPanel copyRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        copyRow.add(copyButton);
        outputPanel.add(copyRow, BorderLayout.SOUTH);

        centerPanel.add(inputPanel);
        centerPanel.add(outputPanel);
        add(centerPanel, BorderLayout.CENTER);

        JButton generateButton = new JButton("Generate Summary");
        generateButton.setBackground(ACCENT);
        generateButton.setForeground(Color.WHITE);
        generateButton.addActionListener(this::onGenerateClicked);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(generateButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onGenerateClicked(ActionEvent e) {
        String text = inputArea.getText();
        if (text.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please paste some paper text first.",
                "No Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        outputArea.setText(summaryGenerator.summarize(text));
    }

    private void onUploadClicked(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select a .txt file");
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String content = Files.readString(chooser.getSelectedFile().toPath());
                inputArea.setText(content);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Could not read file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onCopyClicked(ActionEvent e) {
        String text = outputArea.getText();
        if (text.trim().isEmpty()) return;
        Toolkit.getDefaultToolkit().getSystemClipboard()
            .setContents(new StringSelection(text), null);
    }
}