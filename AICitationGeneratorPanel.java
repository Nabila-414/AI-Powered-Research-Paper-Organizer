package com.mycompany.gui;

import com.mycompany.ai.CitationGenerator;
import com.mycompany.ai.Paper;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class AICitationGeneratorPanel extends JPanel {

    private static final Color ACCENT = new Color(15, 110, 86);
    private static final Color OUTPUT_BG = new Color(225, 245, 238);
    private static final Color OUTPUT_TEXT = new Color(8, 80, 65);

    private JTextField authorField;
    private JTextField titleField;
    private JTextField yearField;
    private JTextField journalField;
    private JComboBox<String> styleBox;
    private JTextArea outputArea;
    private CitationGenerator citationGenerator;

    public AICitationGeneratorPanel() {
        citationGenerator = new CitationGenerator();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("AI Citation Generator");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        authorField = new JTextField(20);
        titleField = new JTextField(20);
        yearField = new JTextField(20);
        journalField = new JTextField(20);
        styleBox = new JComboBox<>(new String[]{"APA", "IEEE"});

        addFormRow(formPanel, gbc, 0, "Author(s):", authorField);
        addFormRow(formPanel, gbc, 1, "Title:", titleField);
        addFormRow(formPanel, gbc, 2, "Year:", yearField);
        addFormRow(formPanel, gbc, 3, "Journal/Conference:", journalField);
        addFormRow(formPanel, gbc, 4, "Citation Style:", styleBox);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        JButton generateButton = new JButton("Generate Citation");
        generateButton.setBackground(ACCENT);
        generateButton.setForeground(Color.WHITE);
        generateButton.addActionListener(this::onGenerateClicked);
        JPanel genBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        genBtnPanel.add(generateButton);
        bottomPanel.add(genBtnPanel, BorderLayout.NORTH);

        outputArea = new JTextArea(3, 30);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        outputArea.setBackground(OUTPUT_BG);
        outputArea.setForeground(OUTPUT_TEXT);
        bottomPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JButton copyButton = new JButton("Copy to Clipboard");
        copyButton.addActionListener(this::onCopyClicked);
        JPanel copyBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        copyBtnPanel.add(copyButton);
        bottomPanel.add(copyBtnPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void onGenerateClicked(ActionEvent e) {
        String author = authorField.getText().trim();
        String title = titleField.getText().trim();
        String year = yearField.getText().trim();
        String journal = journalField.getText().trim();

        if (author.isEmpty() || title.isEmpty() || year.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in at least Author, Title, and Year.",
                "Missing Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Paper paper = new Paper(title, author, year, journal, "");
        String style = (String) styleBox.getSelectedItem();
        outputArea.setText(citationGenerator.generate(paper, style));
    }

    private void onCopyClicked(ActionEvent e) {
        String text = outputArea.getText();
        if (text.trim().isEmpty()) return;
        Toolkit.getDefaultToolkit().getSystemClipboard()
            .setContents(new StringSelection(text), null);
        JOptionPane.showMessageDialog(this, "Citation copied to clipboard!");
    }
}