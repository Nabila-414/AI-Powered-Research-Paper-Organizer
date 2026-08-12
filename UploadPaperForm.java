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
import java.time.Year;

/**
 * Dialog used both for adding a new paper and editing an existing one.
 * Custom-styled to match the "Upload Paper" mockup: blue header bar, PDF
 * document graphic, year dropdown, and a required-PDF check. Note: this dialog
 * is undecorated so the header can be styled blue. The window only supports
 * Close (X) and Maximize/Restore — true "minimize" isn't meaningful for a
 * dialog, so that button is inert.
 */
public class UploadPaperForm extends JDialog {

    private static final Color PRIMARY_BLUE = new Color(0x15, 0x65, 0xC0);
    private static final Color DARK_BLUE = new Color(0x0D, 0x47, 0xA1);
    private static final Color LIGHT_BLUE = new Color(0xE3, 0xF2, 0xFD);
    private static final Color BACKGROUND = new Color(0xF5, 0xF7, 0xFA);
    private static final Color CARD = Color.WHITE;
    private static final Color EDIT_ACCENT = new Color(0xF9, 0xA8, 0x25);
    private static final Color DELETE_ACCENT = new Color(0xD3, 0x2F, 0x2F);

    private static final Color ACCENT_BLUE = PRIMARY_BLUE;
    private static final Color TEXT_DARK = new Color(0x1A, 0x22, 0x33);
    private static final Color TEXT_MUTED = new Color(0x8A, 0x93, 0xA6);
    private static final Color BORDER_LIGHT = new Color(0xDD, 0xE1, 0xE8);
    private static final Color REQUIRED_RED = DELETE_ACCENT;
    private static final Color PDF_RED = new Color(0xE5, 0x3E, 0x3E);
    private static final Color PAGE_GRAY = new Color(0xE8, 0xEA, 0xEE);
    private static final Color PAGE_FOLD = new Color(0xCE, 0xD2, 0xDA);

    private final PaperController controller;
    private final Paper existingPaper;

    private final PlaceholderField titleField = new PlaceholderField("Enter paper title");
    private final PlaceholderField authorField = new PlaceholderField("Enter authors (comma separated)");
    private final JComboBox<Integer> yearCombo;
    private final PlaceholderField categoryField = new PlaceholderField("Enter venue / journal / conference");
    private final PlaceholderField keywordsField = new PlaceholderField("Enter keywords (comma separated)");
    private final JLabel pdfLabel = new JLabel("No file chosen");
    private final JLabel titleBarLabel;

    private File selectedPdf;
    private Rectangle restoreBounds;
    private Point dragOffset;

    public UploadPaperForm(JFrame parent, PaperController controller, Paper existingPaper) {
        super(parent, true);
        this.controller = controller;
        this.existingPaper = existingPaper;

        setUndecorated(true);
        setSize(760, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        ((JComponent) getContentPane()).setBorder(new LineBorder(BORDER_LIGHT, 1));

        int currentYear = Year.now().getValue();
        Integer[] years = new Integer[40];
        for (int i = 0; i < years.length; i++) {
            years[i] = currentYear + 1 - i;
        }
        yearCombo = new JComboBox<>(years);
        yearCombo.setSelectedItem(currentYear);

        titleBarLabel = new JLabel(existingPaper == null ? "Add New Paper" : "Edit Paper");
        add(buildHeader(), BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);

        if (existingPaper != null) {
            populateFromExisting();
        }
    }

    // ---------- Header ----------
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ACCENT_BLUE);
        header.setPreferredSize(new Dimension(0, 42));
        header.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 10));

        titleBarLabel.setForeground(Color.WHITE);
        titleBarLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        controls.setOpaque(false);
        controls.add(windowButton(new MinimizeIcon(), e -> {
        })); // inert: no true minimize for a dialog
        controls.add(windowButton(new MaximizeIcon(), e -> toggleMaximize()));
        controls.add(windowButton(new CloseIcon(), e -> dispose()));

        header.add(titleBarLabel, BorderLayout.WEST);
        header.add(controls, BorderLayout.EAST);

        MouseAdapter dragHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragOffset = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point loc = getLocation();
                setLocation(loc.x + e.getX() - dragOffset.x, loc.y + e.getY() - dragOffset.y);
            }
        };
        header.addMouseListener(dragHandler);
        header.addMouseMotionListener(dragHandler);

        return header;
    }

    private JButton windowButton(Icon icon, ActionListener listener) {
        JButton btn = new JButton(icon);
        btn.setPreferredSize(new Dimension(22, 22));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(listener);
        return btn;
    }

    private void toggleMaximize() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        if (restoreBounds == null) {
            restoreBounds = getBounds();
            Rectangle screen = gc.getBounds();
            setBounds(screen.x, screen.y, screen.width, screen.height);
        } else {
            setBounds(restoreBounds);
            restoreBounds = null;
        }
    }

    // ---------- Body ----------
    private JPanel buildBody() {
        JPanel body = new JPanel(new BorderLayout(24, 0));
        body.setBackground(Color.WHITE);
        body.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        body.add(buildFormColumn(), BorderLayout.CENTER);
        body.add(buildGraphicColumn(), BorderLayout.EAST);
        return body;
    }

    private JPanel buildFormColumn() {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(Color.WHITE);

        JLabel heading = new JLabel("Upload Paper");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setForeground(TEXT_DARK);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        col.add(heading);

        col.add(formRow("Title", true, titleField));
        col.add(formRow("Authors", true, authorField));
        col.add(formRow("Published Year", true, yearCombo));
        col.add(formRow("Venue", true, categoryField));
        col.add(formRow("Keywords", false, keywordsField));
        col.add(buildPdfRow());
        col.add(Box.createVerticalGlue());
        col.add(buildButtonRow());

        return col;
    }

    private JPanel formRow(String label, boolean required, JComponent field) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setBackground(Color.WHITE);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));

        row.add(labelWithAsterisk(label, required));
        row.add(Box.createVerticalStrut(4));

        styleField(field);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        row.add(field);
        return row;
    }

    private JPanel labelWithAsterisk(String text, boolean required) {
        JPanel wrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrap.setOpaque(false);
        wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(TEXT_DARK);
        wrap.add(l);
        if (required) {
            JLabel star = new JLabel(" *");
            star.setFont(new Font("Segoe UI", Font.BOLD, 12));
            star.setForeground(REQUIRED_RED);
            wrap.add(star);
        }
        return wrap;
    }

    private void addSecondaryHover(JButton btn) {
        Color normal = new Color(0xF3, 0xF4, 0xF7);
        Color hover = new Color(0xE6, 0xE8, 0xEC);
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

    private void styleField(JComponent field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(new CompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
    }

    private JPanel buildPdfRow() {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setBackground(Color.WHITE);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        row.add(labelWithAsterisk("PDF File", true));
        row.add(Box.createVerticalStrut(4));

        JPanel pdfPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pdfPanel.setOpaque(false);
        pdfPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton chooseBtn = new JButton("Choose File");
        chooseBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chooseBtn.setBorder(new CompoundBorder(new LineBorder(BORDER_LIGHT, 1, true),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        chooseBtn.setFocusPainted(false);
        chooseBtn.setBackground(new Color(0xF3, 0xF4, 0xF7));
        chooseBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addSecondaryHover(chooseBtn);
        chooseBtn.addActionListener(e -> onChoosePdf());

        pdfLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pdfLabel.setForeground(TEXT_MUTED);

        pdfPanel.add(chooseBtn);
        pdfPanel.add(pdfLabel);
        row.add(pdfPanel);

        JLabel note = new JLabel("(Only PDF files are allowed)");
        note.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        note.setForeground(TEXT_MUTED);
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        note.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        row.add(note);

        return row;
    }

    private JPanel buildButtonRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        JButton clearBtn = new JButton("Clear");
        clearBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        clearBtn.setBorder(new CompoundBorder(new LineBorder(BORDER_LIGHT, 1, true),
                BorderFactory.createEmptyBorder(9, 20, 9, 20)));
        clearBtn.setFocusPainted(false);
        clearBtn.setBackground(new Color(0xF3, 0xF4, 0xF7));
        clearBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addSecondaryHover(clearBtn);
        clearBtn.addActionListener(e -> clearForm());

        Color saveColor = existingPaper == null ?   
    
PRIMARY_BLUE
