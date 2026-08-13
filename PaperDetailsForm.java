/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.papermanager;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;

/**
 * Read-only dialog showing the full details of a single paper. Requires a
 * PaperController so it can open the PDF directly from here, matching the
 * sidebar/row Open PDF behavior exactly (same validation, same "file not found"
 * handling). Visual-only redesign in Phase 3 — no functional changes from the
 * Phase 2 version.
 */
public class PaperDetailsForm extends JDialog {

    private static final Color PRIMARY_BLUE = new Color(0x15, 0x65, 0xC0);
    private static final Color LIGHT_BLUE = new Color(0xE3, 0xF2, 0xFD);
    private static final Color BACKGROUND = new Color(0xF5, 0xF7, 0xFA);
    private static final Color CARD = Color.WHITE;
    private static final Color TEXT_DARK = new Color(0x1A, 0x22, 0x33);
    private static final Color TEXT_MUTED = new Color(0x8A, 0x93, 0xA6);
    private static final Color BORDER_LIGHT = new Color(0xDD, 0xE1, 0xE8);
    private static final Color PDF_RED = new Color(0xE5, 0x3E, 0x3E);

    private final PaperController controller;
    private final Paper paper;

    public PaperDetailsForm(JFrame parent, PaperController controller, Paper paper) {
        super(parent, "Paper Details", true);
        this.controller = controller;
        this.paper = paper;

        setSize(520, 560);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND);

        add(buildBanner(), BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    // ---------- Banner ----------
    private JPanel buildBanner() {
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(PRIMARY_BLUE);
        banner.setPreferredSize(new Dimension(0, 56));
        banner.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("Paper Details");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        banner.add(title, BorderLayout.WEST);
        return banner;
    }

    // ---------- Body ----------
    private JScrollPane buildBody() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD);
        card.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                new LineBorder(BORDER_LIGHT, 1, true)));

        addField(card, "Title", paper.getTitle());
        addField(card, "Authors", paper.getAuthor());
        addField(card, "Published Year", paper.getYear());
        addField(card, "Venue", paper.getCategory());
        addField(card, "Keywords", paper.getKeywords());
        card.add(buildPdfSection());
        addField(card, "Added On", paper.getDateAdded());
        addField(card, "Last Updated", paper.getLastUpdated());
        card.add(buildAbstractSection());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BACKGROUND);
        wrapper.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        wrapper.add(card, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        return scroll;
    }

    private void addField(JPanel card, String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(CARD);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelComp.setForeground(TEXT_MUTED);

        JLabel valueComp = new JLabel(value == null || value.isEmpty() ? "-" : value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valueComp.setForeground(TEXT_DARK);
        valueComp.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

        row.add(labelComp, BorderLayout.NORTH);
        row.add(valueComp, BorderLayout.CENTER);
        card.add(row);
    }

    /**
     * The PDF file gets its own visually distinct light-blue section, per the
     * redesign spec.
     */
    private JPanel buildPdfSection() {
        JPanel section = new JPanel(new BorderLayout(10, 0));
        section.setBackground(LIGHT_BLUE);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 12, 0),
                BorderFactory.createEmptyBorder()));

        JPanel inner = new JPanel(new BorderLayout(10, 0));
        inner.setBackground(LIGHT_BLUE);
        inner.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JComponent badge = buildPdfBadge();
        JLabel fileLabel = new JLabel(paper.hasPdf() ? fileNameOf(paper.getPdfPath()) : "No PDF attached");
        fileLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        fileLabel.setForeground(TEXT_DARK);

        inner.add(badge, BorderLayout.WEST);
        inner.add(fileLabel, BorderLayout.CENTER);
        section.add(inner, BorderLayout.CENTER);
        return section;
    }

    private JComponent buildPdfBadge() {
        JComponent badge = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PDF_RED);
                g2.fill(new RoundRectangle2D.Double(0, 0, 32, 32, 6, 6));
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 9));
                g2.drawString("PDF", 5, 20);
                g2.dispose();
            }
        };
        badge.setPreferredSize(new Dimension(32, 32));
        badge.setMinimumSize(new Dimension(32, 32));
        badge.setMaximumSize(new Dimension(32, 32));
        return badge;
    }

    private JPanel buildAbstractSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(CARD);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        JLabel label = new JLabel("Abstract");
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT_MUTED);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        JTextArea abstractArea = new JTextArea(
                paper.getAbstractText() == null || paper.getAbstractText().isEmpty()
                ? "(no abstract provided)" : paper.getAbstractText());
        abstractArea.setLineWrap(true);
        abstractArea.setWrapStyleWord(true);
        abstractArea.setEditable(false);
        abstractArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        abstractArea.setForeground(TEXT_DARK);
        abstractArea.setBackground(BACKGROUND);
        abstractArea.setBorder(new CompoundBorder(new LineBorder(BORDER_LIGHT, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        abstractArea.setRows(4);

        section.add(label, BorderLayout.NORTH);
        section.add(abstractArea, BorderLayout.CENTER);
        return section;
    }

    // ---------- Footer ----------
    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        footer.setBackground(BACKGROUND);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 16, 12, 16));

        JButton backBtn = new JButton("Back to List");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        backBtn.setBorder(new CompoundBorder(new LineBorder(BORDER_LIGHT, 1, true),
                BorderFactory.createEmptyBorder(9, 18, 9, 18)));
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(0xF3, 0xF4, 0xF7));
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addHoverTint(backBtn, new Color(0xF3, 0xF4, 0xF7), new Color(0xE6, 0xE8, 0xEC));
        backBtn.addActionListener(e -> dispose());

        SolidButton openPdfBtn = new SolidButton("Open PDF", PRIMARY_BLUE, Color.WHITE);
        openPdfBtn.setBorder(BorderFactory.createEmptyBorder(9, 20, 9, 20));
        openPdfBtn.addActionListener(e -> onOpenPdf());

        footer.add(backBtn);
        footer.add(openPdfBtn);
        return footer;
    }

    private void onOpenPdf() {
        if (!paper.hasPdf()) {
            JOptionPane.showMessageDialog(this, "PDF file not found.", "Cannot Open PDF", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean opened = controller.openPdf(paper.getId());
        if (!opened) {
            JOptionPane.showMessageDialog(this, "PDF file not found.", "Cannot Open PDF", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String fileNameOf(String path) {
        return new File(path).getName();
    }

    private void addHoverTint(JButton btn, Color normal, Color hover) {
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(normal);
            }
        });
    }

    // ---------- Self-painted solid button (reliable background on Windows L&F) ----------
    private static class SolidButton extends JButton {

        private final Color fill;
        private final Color hoverFill;
        private boolean hovering;

        SolidButton(String text, Color fill, Color textColor) {
            super(text);
            this.fill = fill;
            this.hoverFill = fill.darker();
            setForeground(textColor);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovering = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovering = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hovering ? hoverFill : fill);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
