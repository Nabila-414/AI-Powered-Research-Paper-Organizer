# AI-Powered Research Paper Organizer

> A Java Swing desktop application for managing, organizing, searching, reading, and analyzing academic research papers with locally implemented AI-assisted features.

---

## 📌 Project Overview

The **AI-Powered Research Paper Organizer** is a Java-based desktop application developed as a group project for the **Object-Oriented Programming (OOP)** course at **Daffodil International University**.

The purpose of the system is to provide students and researchers with a centralized environment for managing academic research papers.

Instead of maintaining research papers across multiple folders and disconnected tools, users can manage paper information, organize papers into categories, mark favorites, search their collection, open associated PDF files, track reading activities, and use AI-assisted features for summary generation, keyword extraction, citation generation, and paper recommendation.

The application is developed using **Java Swing** and follows an object-oriented, modular architecture with local persistence.

---

# 🎯 Objectives

The main objectives of the project are:

- To provide a centralized research-paper management system.
- To allow users to add, edit, view, and delete research papers.
- To organize papers using categories and favorites.
- To provide search and filtering functionality.
- To allow users to associate and open research-paper PDF files.
- To assist users with paper summaries and keyword extraction.
- To generate citations in supported citation formats.
- To recommend related papers using keyword-based similarity.
- To provide reading-planning and statistical functionality.
- To demonstrate practical application of Java OOP principles.
- To develop a modular and maintainable desktop application.

---

# ✨ Key Features

## 👤 User Authentication & Profile

- User registration
- User login
- User profile management
- Password change
- Logout

## 📚 Research Paper Management

- Add research papers
- Edit paper information
- Delete papers
- View paper details
- Manage paper metadata
- Associate PDF files with papers
- Open PDF files directly from the application

## 🔎 Search & Organization

- Search research papers
- Filter and organize papers
- Categorize papers
- Manage favorite papers
- Access recently opened papers

## 🤖 AI-Assisted Features

- Paper summary generation
- Keyword extraction
- Citation generation
- APA citation support
- IEEE citation support
- Keyword-based paper recommendation

> **Note:** The current prototype uses locally implemented/rule-based algorithms for AI-assisted functionality. It does not require an external Large Language Model or paid AI API.

## 📊 Dashboard & Analytics

- Central dashboard
- Reading statistics
- Reading progress
- Reading planner
- Research activity overview

---

# 🏗️ System Architecture

The application follows a modular object-oriented architecture that separates presentation, control, business logic, domain models, services, and persistence responsibilities.

### High-Level Architecture

```text
User
  │
  ▼
Home / Login / Register
  │
  ▼
Dashboard
  │
  ▼
Module Panel / Form
  │
  ▼
Controller
  │
  ▼
Manager / Service
  │
  ▼
Model
  │
  ▼
Local Persistence / File Repository

| Member | Student Name           | Student ID | Assigned Module                            |
| ------ | ---------------------- | ---------- | ------------------------------------------ |
| 1      | Jamsad Chowdhury       | 241-15-028 | User Authentication & Profile              |
| 2      | Md Azimul Islam Sarker | 251-15-377 | Research Paper Management                  |
| 3      | Abtahi Ferdous Mahi    | 251-15-312 | Search & Organization                      |
| 4      | Nabila Mahdia          | 242-15-414 | AI Features                                |
| 5      | Tahmina Parvej         | 251-15-178 | Dashboard & Analytics / System Integration |

| Technology                            | Purpose                            |
| ------------------------------------- | ---------------------------------- |
| **Java**                              | Core application development       |
| **Java Swing**                        | Desktop graphical user interface   |
| **OOP**                               | Object-oriented system design      |
| **Java Collections**                  | Data management                    |
| **File I/O**                          | File operations                    |
| **Serialization / Local Persistence** | Local data storage                 |
| **PDF File Handling**                 | Research-paper document management |
| **Git**                               | Version control                    |
| **GitHub**                            | Repository and collaboration       |
| **NetBeans / IntelliJ / Eclipse**     | Development environment            |

ResearchPaperOrganizer/
│
├── src/
│   │
│   ├── model/
│   │   ├── User.java
│   │   ├── Paper.java
│   │   ├── Category.java
│   │   └── ...
│   │
│   ├── manager/
│   │   ├── PaperManager.java
│   │   ├── SearchManager.java
│   │   ├── FavoriteManager.java
│   │   ├── StatisticsManager.java
│   │   └── ...
│   │
│   ├── controller/
│   │   ├── PaperController.java
│   │   ├── LoginController.java
│   │   ├── RegisterController.java
│   │   └── ...
│   │
│   ├── gui/
│   │   ├── DashboardForm.java
│   │   ├── PaperManagementPanel.java
│   │   ├── UploadPaperForm.java
│   │   ├── PaperListForm.java
│   │   ├── PaperDetailsForm.java
│   │   └── ...
│   │
│   ├── ai/
│   │   ├── AIService.java
│   │   ├── SummaryGenerator.java
│   │   ├── KeywordExtractor.java
│   │   ├── CitationGenerator.java
│   │   └── RecommendationEngine.java
│   │
│   └── util/
│       └── ...
│
├── report/
│   └── Project Report
│
├── README.md
└── ...
🧪 Testing

The project should be tested through the major user workflows.
| Test Area          | Expected Result                            |
| ------------------ | ------------------------------------------ |
| User Registration  | New user can be registered                 |
| User Login         | Valid credentials allow access             |
| Add Paper          | New paper is added successfully            |
| Edit Paper         | Existing paper information is updated      |
| Delete Paper       | Selected paper is removed                  |
| Search             | Relevant papers are displayed              |
| Categorization     | Papers can be organized by category        |
| Favorites          | Papers can be marked/unmarked as favorites |
| PDF Opening        | Associated PDF opens successfully          |
| Summary            | Paper summary is generated                 |
| Keyword Extraction | Relevant keywords are identified           |
| Citation           | Supported citation format is generated     |
| Recommendation     | Related papers are identified              |
| Reading Planner    | Reading activities can be managed          |
| Statistics         | Research/reading information is displayed  |

⚠️ Current Limitations

The current prototype has several limitations:

Desktop-only deployment
Local file/serialization-based persistence
Limited scalability for very large paper collections
Local/rule-based AI-assisted processing
Production-grade password security requires further improvement
Limited automated test coverage
No cloud synchronization
No collaborative research library
No mobile application

Future Improvements

Future versions may include:

Relational database integration
JDBC-based persistence
Secure password hashing and stronger authentication
Cloud synchronization
Automated backup
Collaborative research libraries
Full-text indexing
Semantic/vector-based paper recommendation
Optional external AI API integration
Improved citation validation
Automated unit and integration testing
Continuous testing through GitHub workflows
Web and mobile versions

Course Information

Course: Object-Oriented Programming (OOP)

Department:
Department of Computer Science and Engineering

University:
Daffodil International University

Course Instructor:
Md. Mezbaul Islam Zion
Lecturer
Department of Computer Science and Engineering
Daffodil International University

🎓 Academic Purpose

This project was developed as an academic software project to demonstrate practical knowledge of:

Java
Object-Oriented Programming
GUI Development
Software Architecture
Data Structures and Collections
File Handling
Persistence
Exception Handling
Text Processing
Modular Software Development
Software Testing
Technical Documentation
