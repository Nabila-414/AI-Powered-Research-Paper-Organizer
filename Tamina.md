import java.time.LocalDate;


public class Book {
    private int id;
    private String title;
    private String author;
    private int totalPages;
    private int pagesRead;
    private String status;      
    private LocalDate dateAdded;
    private LocalDate targetDate; 

    public Book(int id, String title, String author, int totalPages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.pagesRead = 0;
        this.status = "Not Started";
        this.dateAdded = LocalDate.now();
        this.targetDate = null;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getAuthor() { return author; }

    public int getTotalPages() { return totalPages; }

    public int getPagesRead() { return pagesRead; }

    public void setPagesRead(int pagesRead) {
        if (pagesRead < 0) pagesRead = 0;
        if (pagesRead > totalPages) pagesRead = totalPages;
        this.pagesRead = pagesRead;

        if (this.pagesRead == 0) {
            this.status = "Not Started";
        } else if (this.pagesRead >= totalPages) {
            this.status = "Completed";
        } else {
            this.status = "Reading";
        }
    }

    public String getStatus() { return status; }

    public LocalDate getDateAdded() { return dateAdded; }

    public LocalDate getTargetDate() { return targetDate; }

    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public double getProgressPercent() {
        if (totalPages == 0) return 0;
        return (pagesRead * 100.0) / totalPages;
    }

    public String toCsvLine() {
        return id + "," + title + "," + author + "," + totalPages + "," +
                pagesRead + "," + status + "," + dateAdded + "," +
                (targetDate == null ? "NULL" : targetDate);
    }

    public static Book fromCsvLine(String line) {
        String[] parts = line.split(",", -1);
        Book b = new Book(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]));
        b.setPagesRead(Integer.parseInt(parts[4]));
        b.dateAdded = LocalDate.parse(parts[6]);
        if (!parts[7].equals("NULL")) {
            b.targetDate = LocalDate.parse(parts[7]);
        }
        return b;
    }

    @Override
    public String toString() {
        return String.format("[#%d] %s by %s | %d/%d pages (%.1f%%) | %s",
                id, title, author, pagesRead, totalPages, getProgressPercent(), status);
    }
}
