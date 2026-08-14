package com.mycompany.papermanager;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PaperManager {

    private List<Paper> papers;
    private int nextId;

    private static final String DATA_DIR = "paper_data";
    private static final String DATA_FILE = DATA_DIR + File.separator + "papers.dat";
    private static final String REPO_DIR = DATA_DIR + File.separator + "repository";

   public PaperManager() {
    papers = new ArrayList<>();
    nextId = 1;

    ensureDirs();
    loadPapers();
}

   

    private void ensureDirs() {
        new File(DATA_DIR).mkdirs();
        new File(REPO_DIR).mkdirs();
    }

    /**
     * Original-compatible addPaper method. Creates an unprotected paper.
     */
    public Paper addPaper(
            String title,
            String author,
            String year,
            String category,
            String keywords,
            String abstractText,
            File sourcePdf) throws IOException {

        return addPaper(
                title,
                author,
                year,
                category,
                keywords,
                abstractText,
                sourcePdf,
                null
        );
    }

    /**
     * Adds a paper with optional PDF password protection.
     *
     * @param pdfPasswordHash SHA-256 hash of the application password, or null
     * for no password protection.
     */
    public Paper addPaper(
            String title,
            String author,
            String year,
            String category,
            String keywords,
            String abstractText,
            File sourcePdf,
            String pdfPasswordHash) throws IOException {

        String storedPath = null;

        if (sourcePdf != null) {
            storedPath = copyPdfToRepo(sourcePdf, nextId);
        }

        Paper p = new Paper(
                nextId,
                title,
                author,
                year,
                category,
                keywords,
                abstractText,
                storedPath
        );

        p.setPdfPasswordHash(pdfPasswordHash);

        papers.add(p);
        nextId++;

        savePapers();

        return p;
    }

    /**
     * Original-compatible editPaper method. Keeps existing password protection.
     */
    public boolean editPaper(
            int id,
            String title,
            String author,
            String year,
            String category,
            String keywords,
            String abstractText,
            File newPdf) throws IOException {

        Paper p = getPaperById(id);

        if (p == null) {
            return false;
        }

        String existingPasswordHash = p.getPdfPasswordHash();

        return editPaper(
                id,
                title,
                author,
                year,
                category,
                keywords,
                abstractText,
                newPdf,
                existingPasswordHash
        );
    }

    /**
     * Edits a paper and updates optional PDF password protection.
     *
     * @param pdfPasswordHash SHA-256 hash or null for no protection.
     */
    public boolean editPaper(
            int id,
            String title,
            String author,
            String year,
            String category,
            String keywords,
            String abstractText,
            File newPdf,
            String pdfPasswordHash) throws IOException {

        Paper p = getPaperById(id);

        if (p == null) {
            return false;
        }

        p.setTitle(title);
        p.setAuthor(author);
        p.setYear(year);
        p.setCategory(category);
        p.setKeywords(keywords);
        p.setAbstractText(abstractText);

        if (newPdf != null) {
            String storedPath = copyPdfToRepo(newPdf, id);
            p.setPdfPath(storedPath);
        }

        p.setPdfPasswordHash(pdfPasswordHash);
        p.touch();

        savePapers();

        return true;
    }

    public boolean deletePaper(int id) {

        Paper p = getPaperById(id);

        if (p == null) {
            return false;
        }

        if (p.hasPdf()) {
            File f = new File(p.getPdfPath());

            if (f.exists()) {
                f.delete();
            }
        }

        papers.remove(p);

        savePapers();

        return true;
    }

    public Paper getPaperById(int id) {

        for (Paper p : papers) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    public List<Paper> getAllPapers() {
        return papers;
    }

    private String copyPdfToRepo(File source, int id) throws IOException {

        String safeName
                = "paper_"
                + id
                + "_"
                + source.getName().replaceAll("[^a-zA-Z0-9._-]", "_");

        Path target = Paths.get(REPO_DIR, safeName);

        Files.copy(
                source.toPath(),
                target,
                StandardCopyOption.REPLACE_EXISTING
        );

        return target.toString();
    }

    @SuppressWarnings("unchecked")
    private void loadPapers() {

        File f = new File(DATA_FILE);

        if (!f.exists()) {
            return;
        }

        try (ObjectInputStream ois
                = new ObjectInputStream(
                        new FileInputStream(f))) {

            papers = (List<Paper>) ois.readObject();

            for (Paper p : papers) {

                if (p.getId() >= nextId) {
                    nextId = p.getId() + 1;
                }

                /*
                 * Old Paper objects created before the password feature
                 * will simply have a null password hash.
                 * That means they remain unprotected.
                 */
                if (p.getPdfPasswordHash() == null) {
                    p.setPdfPasswordHash(null);
                }
            }

        } catch (Exception e) {

            System.err.println(
                    "Failed to load paper data: "
                    + e.getMessage()
            );
        }
    }

    private void savePapers() {

        try (ObjectOutputStream oos
                = new ObjectOutputStream(
                        new FileOutputStream(DATA_FILE))) {

            oos.writeObject(papers);

        } catch (IOException e) {

            System.err.println(
                    "Failed to save paper data: "
                    + e.getMessage()
            );
        }
    }
}
