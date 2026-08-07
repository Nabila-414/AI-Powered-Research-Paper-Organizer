# AI Powered Research Paper Organizer — Member 3 Module
## Search & Organization (Java + Java Swing)

This folder contains the complete, working code for **Member 3's part** of the
group project: **Search & Organization**.

## What's included

| Type    | Files |
|---------|-------|
| Models  | `model/Paper.java`, `model/Category.java` |
| Managers (logic) | `manager/SearchManager.java`, `manager/FavoriteManager.java` |
| GUI     | `gui/SearchForm.java`, `gui/CategoryForm.java`, `gui/FavoriteForm.java`, `gui/MainApp.java` |
| Utility | `util/SampleData.java` (dummy papers for testing) |
| Resource | `logo.png` (your team logo, shown as window icon + header) |

## Features implemented (matches your module list)

- **Search Papers** — search by keyword (title/author)
- **Filter** — by Author, Year, Category (individually or all together)
- **Categories** — browse/add categories, see papers per category
- **Bookmark / Favorite** — mark/unmark papers as favorite
- **Recent Papers** — automatically tracks last-opened papers

Window is fixed at **1280x720** as requested.

## How to run

### Option A — Command line
```bash
cd ResearchPaperOrganizer
javac -d bin -cp src src/model/*.java src/manager/*.java src/util/*.java src/gui/*.java
cp logo.png bin/    # so the app can find the logo at runtime
java -cp bin gui.MainApp
```

### Option B — IntelliJ IDEA / Eclipse / NetBeans
1. Create a new **Java project**.
2. Copy the `model`, `manager`, `gui`, `util` folders into your `src` folder.
3. Put `logo.png` in the same source root (or mark a `resources` folder as a
   source/resource root and put it there) so `getClass().getClassLoader().getResource("logo.png")` finds it.
4. Run `gui.MainApp`.

## Important note for merging with your teammates

- `model/Paper.java` here is a **simplified stand-in** — in the real merged
  project, Member 2 owns the real `Paper` class (with more fields like PDF
  metadata, etc). When you merge branches:
  1. Delete this `Paper.java`.
  2. Use Member 2's `Paper.java` instead.
  3. If their field/getter names differ, just update the getter calls inside
     `SearchManager.java` and `FavoriteManager.java` to match (e.g. `getTitle()`,
     `getAuthor()`, `getYear()`, `getCategory()`).
- `util/SampleData.java` is only for **testing this module standalone**. Once
  merged, replace it with real data coming from Member 2's `PaperManager`
  (database) — just pass that `List<Paper>` into `SearchManager` and
  `FavoriteManager` instead of `SampleData.getSamplePapers()`.
- The 3 panels (`SearchForm`, `CategoryForm`, `FavoriteForm`) are plain
  `JPanel`s, so in the final merged app your team can just add them as tabs
  (or menu items) inside one shared main window with everyone else's panels —
  see how `MainApp.java` does it with `JTabbedPane`.

## GitHub upload tips

Suggested repo structure:
```
ResearchPaperOrganizer/
 ├── src/
 │   ├── model/
 │   ├── manager/
 │   ├── gui/
 │   └── util/
 ├── resources/ (or root) — logo.png
 └── README.md
```
Add a `.gitignore` with:
```
bin/
*.class
```
