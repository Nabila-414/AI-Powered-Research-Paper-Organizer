# AI Powered Research Paper Organizer - Member 3 Part

This is my part of our group project. My module is Search & Organization.

Group Project: AI Powered Research Paper Organizer
My Role: Member 3 - Search & Organization

## What I built

- Search Papers - search papers by title or author
- Filter - filter by Author, Year, Category
- Categories - browse papers by category
- Bookmark / Favorite - mark papers as favorite
- Recent Papers - shows recently opened papers

## Files

src/model - Paper.java, Category.java
src/manager - SearchManager.java, FavoriteManager.java
src/gui - SearchForm.java, CategoryForm.java, FavoriteForm.java, MainApp.java
src/util - SampleData.java (dummy/sample papers for testing my part alone)
logo.png - project logo used in the window

## How to run

Using command line:
```
cd ResearchPaperOrganizer
javac -d bin -cp src src/model/*.java src/manager/*.java src/util/*.java src/gui/*.java
cp logo.png bin/
java -cp bin gui.MainApp
```

Or open in NetBeans / Eclipse / IntelliJ:
1. Create a new Java project
2. Copy model, manager, gui, util folders inside src
3. Put logo.png in the source folder
4. Run gui.MainApp

Window size is fixed to 1280x720.

## Note

Right now this runs with sample/dummy data (SampleData.java) so I could test my part alone without waiting for the whole team's code. When we merge everyone's code together, this will connect with Member 2's real Paper class and data instead of the sample data.

The three panels (SearchForm, CategoryForm, FavoriteForm) are just JPanels, so they can be added as tabs inside the team's main combined window later.
