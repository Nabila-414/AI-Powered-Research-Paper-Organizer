package util;

import model.Category;
import model.Paper;

import java.util.ArrayList;
import java.util.List;

/**
 * SampleData.java
 * ----------------
 * Just generates some dummy papers & categories so the GUI has
 * something to show/search/filter while testing. Remove this once
 * you connect it to Member 2's real PaperManager / a real database.
 */
public class SampleData {

    public static List<Paper> getSamplePapers() {
        List<Paper> papers = new ArrayList<>();
        papers.add(new Paper("Deep Learning for Image Recognition", "A. Karim", 2021, "Machine Learning", "papers/dl_image.pdf"));
        papers.add(new Paper("A Survey of Neural Networks", "S. Rahman", 2019, "Machine Learning", "papers/survey_nn.pdf"));
        papers.add(new Paper("Efficient Routing in Wireless Networks", "M. Islam", 2020, "Networking", "papers/routing.pdf"));
        papers.add(new Paper("Database Indexing Techniques", "T. Chowdhury", 2018, "Databases", "papers/db_index.pdf"));
        papers.add(new Paper("NoSQL vs SQL: A Comparative Study", "F. Ahmed", 2022, "Databases", "papers/nosql_sql.pdf"));
        papers.add(new Paper("Transformer Models in NLP", "R. Hasan", 2023, "Machine Learning", "papers/transformers.pdf"));
        papers.add(new Paper("Network Security Fundamentals", "N. Akter", 2021, "Networking", "papers/net_security.pdf"));
        papers.add(new Paper("Cloud Computing Architectures", "J. Alam", 2020, "Cloud Computing", "papers/cloud_arch.pdf"));
        papers.add(new Paper("Blockchain for Data Integrity", "K. Sultana", 2022, "Security", "papers/blockchain.pdf"));
        papers.add(new Paper("Human-Computer Interaction Basics", "P. Das", 2017, "HCI", "papers/hci_basics.pdf"));
        return papers;
    }

    public static List<Category> getSampleCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Machine Learning", "AI, deep learning, neural networks"));
        categories.add(new Category("Networking", "Wireless, routing, protocols"));
        categories.add(new Category("Databases", "SQL, NoSQL, indexing, storage"));
        categories.add(new Category("Cloud Computing", "Cloud architecture & services"));
        categories.add(new Category("Security", "Cybersecurity, blockchain, encryption"));
        categories.add(new Category("HCI", "Human-Computer Interaction & UX"));
        return categories;
    }
}
