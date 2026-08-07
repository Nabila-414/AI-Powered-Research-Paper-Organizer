package gui;

import manager.FavoriteManager;
import manager.SearchManager;
import model.Category;
import model.Paper;
import util.SampleData;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * MainApp.java
 * -------------
 * Entry point for Member 3's module (Search & Organization).
 * Ties together SearchForm, CategoryForm and FavoriteForm inside tabs.
 * Window size fixed to 1280x720 as requested, and uses the project logo
 * (icons8-brain-55.png) as both the window icon and a header image.
 *
 * HOW TO RUN:
 *   javac -d bin $(find src -name "*.java")
 *   java -cp bin gui.MainApp
 */
public class MainApp extends JFrame {

    public MainApp() {
        setTitle("AI Powered Research Paper Organizer - Search & Organization (Member 3)");
        setSize(1280, 720);
        setMinimumSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIconImage(loadLogo());

        setLayout(new BorderLayout());
        add(buildHeader(), BorderLayout.NORTH);
        add(buildTabs(), BorderLayout.CENTER);
    }

    /** Top header bar with logo + project title, shown above the tabs. */
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(33, 37, 41));
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel logoLabel = new JLabel();
        Image logo = loadLogo();
        if (logo != null) {
            logoLabel.setIcon(new ImageIcon(logo.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        }

        JLabel titleLabel = new JLabel("  AI Powered Research Paper Organizer");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setOpaque(false);
        left.add(logoLabel);
        left.add(titleLabel);
        header.add(left, BorderLayout.WEST);

        JLabel subtitle = new JLabel("Module: Search & Organization  ");
        subtitle.setForeground(new Color(200, 200, 200));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        header.add(subtitle, BorderLayout.EAST);

        return header;
    }

    /** Builds the tabbed pane containing the three GUI forms for this module. */
    private JTabbedPane buildTabs() {
        // Shared in-memory data (in the real project this would come from a database via Member 2's PaperManager)
        List<Paper> allPapers = SampleData.getSamplePapers();
        List<Category> allCategories = SampleData.getSampleCategories();

        SearchManager searchManager = new SearchManager(allPapers);
        FavoriteManager favoriteManager = new FavoriteManager(allPapers);

        SearchForm searchForm = new SearchForm(searchManager, favoriteManager, allCategories);
        CategoryForm categoryForm = new CategoryForm(allCategories, searchManager);
        FavoriteForm favoriteForm = new FavoriteForm(favoriteManager);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabs.addTab("Search Papers", searchForm);
        tabs.addTab("Categories", categoryForm);
        tabs.addTab("Favorites / Recent", favoriteForm);

        // whenever user switches to the Favorites tab, refresh it so newly
        // bookmarked / opened papers from the Search tab show up immediately
        tabs.addChangeListener(e -> {
            if (tabs.getSelectedComponent() == favoriteForm) {
                favoriteForm.refresh();
            }
        });

        return tabs;
    }

    /** Loads resources/logo.png (works both when run from source and from a packaged jar). */
    private Image loadLogo() {
        try {
            URL url = getClass().getClassLoader().getResource("logo.png");
            if (url != null) {
                return new ImageIcon(url).getImage();
            }
            // fallback: try loading directly from the resources folder during development
            return new ImageIcon("resources/logo.png").getImage();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        // Use the system look and feel so it matches Windows/Mac/Linux styling
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.setVisible(true);
        });
    }
}
