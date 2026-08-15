# AI Powered Research Paper Organizer

A desktop application built with Java Swing that helps students and researchers manage, search, and organize their research papers in one place, with AI-assisted features for summarizing and citing papers.

---

## 1. Introduction

Research Paper Organizer is a Java Swing based desktop application developed as a group project. The goal of this project is to give users a single tool where they can add and manage their research papers, search and filter them, organize papers into categories, bookmark favorites, get AI-generated summaries and citations, and track their reading progress through a dashboard.

The application follows a simple MVC (Model-View-Controller) style structure and uses file-based storage, so no external database setup is required to run it.

---

## 2. Key Features

**User Authentication & Profile**
- Login and Registration
- User Profile management
- Change Password
- Logout

**Research Paper Management**
- Add, edit, and delete papers
- View paper details
- Open PDF directly from the app

**Search & Organization**
- Search papers by title/author keyword
- Filter by Author, Year, and Category
- Browse papers by category
- Bookmark/Favorite papers
- View recently opened papers

**AI Features**
- AI-generated paper summary
- AI keyword extraction
- AI citation generator (APA/IEEE format)
- AI-based paper recommendations

**Dashboard & Analytics**
- Reading statistics and progress tracking
- Reading planner
- Combined dashboard for the whole application

---

## 3. Team Task Distribution

| Member | Student Name | Student ID | Assigned Module | Main Contribution |
|--------|--------------|------------|------------------|--------------------|
| 1 | Jamsad Chowdhury | 241-15-028 | User Authentication & Profile | User, LoginController, RegisterController, UserProfile, LoginForm, RegisterForm, ProfileForm |
| 2 | Md Azimul Islam Sarker | 251-15-377 | Research Paper Management | Paper, PaperManager, PaperController, UploadPaperForm, PaperListForm, PaperDetailsForm |
| 3 | Abtahi Ferdous Mahi | 251-15-312 | Search & Organization | Category, SearchManager, FavoriteManager, SearchForm, CategoryForm, FavoriteForm |
| 4 | Nabila Mahdia | 242-15-414 | AI Features | AIService, SummaryGenerator, CitationGenerator, RecommendationEngine, AI Assistant Form, Summary Panel, Citation Panel |
| 5 | Tahmina Parvej | 251-15-178 | Dashboard & Analytics, System Integration | DashboardManager, StatisticsManager, ReadingPlanner, DatabaseManager, Dashboard, Statistics, Reading Planner |

---

## 4. Citation & Report

Full project report, including detailed module descriptions, design diagrams, and citations of resources/tools used, is included in the repository. Please refer to the project report document for complete academic references.

---

## 5. Course Instructor

**Md. Mezbaul Islam Zion**
Lecturer,
Department of Computer Science and Engineering,
Daffodil International University

---

## Project Structure

```
ResearchPaperOrganizer/
├── src/
│   ├── model/          # Data classes: User, Paper, Category, etc.
│   ├── manager/         # Business logic: SearchManager, FavoriteManager, etc.
│   ├── controller/       # Controllers: LoginController, PaperController, etc.
│   ├── gui/              # Swing forms: LoginForm, SearchForm, Dashboard, etc.
│   └── util/            # Helper/sample data classes
├── README.md
└── report/               # Project report and documentation
```

## How to Run

1. Clone this repository
2. Open the project in NetBeans / Eclipse / IntelliJ
3. Make sure all module folders are inside `src`
4. Run `MainApp.java` (or the main class of the integrated project)

---

*Daffodil International University*
