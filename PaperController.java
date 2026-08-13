package com.mycompany.papermanager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class PaperController {

    private final PaperManager manager;

    public PaperController() {
        this.manager = new PaperManager();
    }

    public List<Paper> getAllPapers() {
        return manager.getAllPapers();
    }

    public Paper getPaperById(int id) {
        return manager.getPaperById(id);
    }

    /**
     * Original-compatible addPaper method. Adds an unprotected paper.
     */
    public String addPaper(
            String title,
            String author,
            String year,
            String category,
            String keywords,
            String abstractText,
            File pdf) {

        return addPaper(
                title,
                author,
                year,
                category,
                keywords,
                abstractText,
                pdf,
                null
        );
    }

    /**
     * Adds a paper with optional application-level PDF password protection.
     *
     * @param password Plain-text password entered by the user, or null/empty
     * for no protection.
     */
    public String addPaper(
            String title,
            String author,
            String year,
            String category,
            String keywords,
            String abstractText,
            File pdf,
            String password) {

        String error = validate(
                title,
                author,
                year,
                category
        );

        if (error != null) {
            return error;
        }

        try {

            String passwordHash = null;

            if (password != null && !password.isEmpty()) {
                passwordHash = hashPassword(password);
            }

            manager.addPaper(
                    title.trim(),
                    author.trim(),
                    safe(year),
                    category.trim(),
                    safe(keywords),
                    safe(abstractText),
                    pdf,
                    passwordHash
            );

            return null;

        } catch (IOException e) {

            return "Failed to save PDF file: "
                    + e.getMessage();
        }
    }

    /**
     * Original-compatible editPaper method. Keeps the existing password
     * setting.
     */
    public String editPaper(
            int id,
            String title,
            String author,
            String year,
            String category,
            String keywords,
            String abstractText,
            File pdf) {

        Paper existing = manager.getPaperById(id);

        String existingHash
                = existing == null
                        ? null
                        : existing.getPdfPasswordHash();

        return editPaperWithHash(
                id,
                title,
                author,
                year,
                category,
                keywords,
                abstractText,
                pdf,
                existingHash
        );
    }

    /**
     * Edits a paper and updates optional password protection.
     *
     * password: - null/empty = no password protection - non-empty = password
     * protection enabled
     */
    public String editPaper(
            int id,
            String title,
            String author,
            String year,
            String category,
            String keywords,
            String abstractText,
            File pdf,
            String password) {

        String error = validate(
                title,
                author,
                year,
                category
        );

        if (error != null) {
            return error;
        }

        try {

            String passwordHash = null;

            if (password != null && !password.isEmpty()) {
                passwordHash = hashPassword(password);
            }

            return editPaperWithHash(
                    id,
                    title,
                    author,
                    year,
                    category,
                    keywords,
                    abstractText,
                    pdf,
                    passwordHash
            );

        } catch (Exception e) {

            return "Failed to update paper: "
                    + e.getMessage();
        }
    }

    private String editPaperWithHash(
            int id,
            String title,
            String author,
            String year,
            String category,
            String keywords,
            String abstractText,
            File pdf,
            String passwordHash) {

        try {

            boolean ok = manager.editPaper(
                    id,
                    title.trim(),
                    author.trim(),
                    safe(year),
                    category.trim(),
                    safe(keywords),
                    safe(abstractText),
                    pdf,
                    passwordHash
            );

            return ok
                    ? null
                    : "Paper not found (it may have been deleted).";

        } catch (IOException e) {

            return "Failed to save PDF file: "
                    + e.getMessage();
        }
    }

    public boolean deletePaper(int id) {
        return manager.deletePaper(id);
    }

    /**
     * Opens a paper's PDF.
     *
     * If password protection is disabled: PDF opens normally.
     *
     * If password protection is enabled: A password dialog appears first.
     */
    public boolean openPdf(int id) {

        Paper p = manager.getPaperById(id);

        if (p == null || !p.hasPdf()) {

            JOptionPane.showMessageDialog(
                    null,
                    "This paper does not have a PDF.",
                    "PDF Not Available",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        File pdfFile = new File(p.getPdfPath());

        if (!pdfFile.exists()) {

            JOptionPane.showMessageDialog(
                    null,
                    "The PDF file could not be found:\n"
                    + pdfFile.getAbsolutePath(),
                    "PDF Not Found",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }

        /*
         * No password protection:
         * open exactly as before.
         */
        if (!p.isPdfPasswordProtected()) {
            return openPdfFile(pdfFile);
        }

        /*
         * Password protection enabled.
         */
        JPasswordField passwordField
                = new JPasswordField();

        passwordField.setPreferredSize(
                new Dimension(240, 30)
        );

        JPanel panel = new JPanel();
        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel label
                = new JLabel(
                        "Enter password to open this PDF:"
                );

        label.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 0, 8, 0
                )
        );

        panel.add(label);
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "PDF Password",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return false;
        }

        String enteredPassword
                = new String(
                        passwordField.getPassword()
                );

        String enteredHash
                = hashPassword(enteredPassword);

        if (!constantTimeEquals(
                enteredHash,
                p.getPdfPasswordHash())) {

            JOptionPane.showMessageDialog(
                    null,
                    "Incorrect password.",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }

        return openPdfFile(pdfFile);
    }

    /**
     * Actually opens the PDF using the operating system.
     */
    private boolean openPdfFile(File pdfFile) {

        try {

            if (Desktop.isDesktopSupported()
                    && Desktop.getDesktop().isSupported(
                            Desktop.Action.OPEN)) {

                Desktop.getDesktop().open(pdfFile);

                return true;
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to open PDF:\n"
                    + e.getMessage(),
                    "Open PDF Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }

        JOptionPane.showMessageDialog(
                null,
                "Your system does not support opening PDF files automatically.",
                "Open PDF Error",
                JOptionPane.ERROR_MESSAGE
        );

        return false;
    }

    /**
     * Converts a password into a SHA-256 hash.
     *
     * The actual password is never stored in the Paper object.
     */
    private String hashPassword(String password) {

        try {

            MessageDigest digest
                    = MessageDigest.getInstance("SHA-256");

            byte[] hash
                    = digest.digest(
                            password.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            StringBuilder hex
                    = new StringBuilder();

            for (byte b : hash) {

                String h
                        = Integer.toHexString(
                                0xff & b
                        );

                if (h.length() == 1) {
                    hex.append('0');
                }

                hex.append(h);
            }

            return hex.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new IllegalStateException(
                    "SHA-256 is not available.",
                    e
            );
        }
    }

    /**
     * Constant-time comparison for password hashes.
     */
    private boolean constantTimeEquals(
            String a,
            String b) {

        if (a == null || b == null) {
            return false;
        }

        return MessageDigest.isEqual(
                a.getBytes(StandardCharsets.UTF_8),
                b.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String validate(
            String title,
            String author,
            String year,
            String category) {

        if (title == null
                || title.trim().isEmpty()) {

            return "Title is required.";
        }

        if (author == null
                || author.trim().isEmpty()) {

            return "Authors is required.";
        }

        if (year == null
                || year.trim().isEmpty()) {

            return "Published Year is required.";
        }

        if (category == null
                || category.trim().isEmpty()) {

            return "Venue is required.";
        }

        return null;
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }
}
