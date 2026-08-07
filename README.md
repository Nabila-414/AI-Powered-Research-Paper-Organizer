# User Authentication & Profile Module
### Member 1 — AI Powered Research Paper Organizer

This folder contains the **complete, working code for Member 1's part only**:
Login, Registration, User Profile, Change Password, Logout.

---

## 📁 Folder Structure

```
AuthModule/
├── src/
│   ├── Main.java                  # run this to launch the module
│   ├── model/
│   │   └── User.java              # data class (fields + getters/setters)
│   ├── controller/
│   │   ├── LoginController.java   # login logic + session (currentUser)
│   │   ├── RegisterController.java# registration validation + save
│   │   └── UserProfile.java       # edit profile / change password / logout
│   ├── gui/
│   │   ├── LoginForm.java         # Login Form (1280x720)
│   │   ├── RegisterForm.java      # Register Form (1280x720)
│   │   └── ProfileForm.java       # Profile Form (1280x720)
│   └── util/
│       ├── FileDatabase.java      # simple file-based storage (users.dat)
│       └── PasswordUtil.java      # SHA-256 password hashing
└── resources/
    └── logo.png                  # your brain/AI icon, used in all 3 windows
```

This maps 1-to-1 to what your project plan asked for:
- **Modules:** Login, Registration, User Profile, Change Password, Logout → covered by `LoginController`, `RegisterController`, `UserProfile`.
- **GUI:** Login Form, Register Form, Profile Form → `LoginForm.java`, `RegisterForm.java`, `ProfileForm.java`.
- **Classes:** User, LoginController, RegisterController, UserProfile → all present with those exact names.

---

## ▶️ How to run

### Option A — IntelliJ IDEA / Eclipse / VS Code (recommended)
1. Open the `AuthModule` folder as a new Java project.
2. Mark `src` as the "Sources Root" (IntelliJ does this automatically).
3. Make sure `resources/logo.png` stays in the project's **root working directory**
   (same level as where you run the program from) — the code loads it with the
   relative path `"resources/logo.png"`.
4. Run `Main.java`.

### Option B — Terminal (javac/java)
```bash
cd AuthModule
javac -d out $(find src -name "*.java")
java -cp out Main
```
(Run this from inside the `AuthModule` folder so it can find `resources/logo.png`
and so it can create `users.dat` in the same place.)

---

## 🧠 How it works (explanation for your viva / GitHub description)

1. **User.java** — just a data holder (`userId, fullName, email, username,
   passwordHash, institution, bio, joinDate`). It never contains logic — that's
   the MVC principle: Model = data only.

2. **FileDatabase.java** — because your team may not have set up the shared
   database yet, this class stores all users inside a file called `users.dat`
   using Java's built-in object serialization. It exposes clean methods
   (`addUser`, `findByUsername`, `updateUser`...) so that later, when
   Member 5's `DatabaseManager` (real MySQL/SQLite) is ready, you can literally
   just swap the *inside* of these methods — nothing in your controllers or
   GUI needs to change.

3. **PasswordUtil.java** — turns the typed password into a SHA-256 hash before
   it's ever saved, so raw passwords are never stored on disk.

4. **RegisterController.java** — validates the form (empty fields, valid
   email format, password length, matching confirm-password, duplicate
   username/email) and, if everything passes, creates a new `User` and saves
   it via `FileDatabase`.

5. **LoginController.java** — looks up the username, checks the password hash,
   and if correct, stores that `User` as `currentUser` (a static "session").
   Every other screen (like ProfileForm) asks `LoginController.getCurrentUser()`
   to know who is logged in.

6. **UserProfile.java** — handles what a *logged-in* user can do afterward:
   edit name/institution/bio, change password (requires the correct old
   password first), and logout (just clears the session).

7. **GUI (LoginForm / RegisterForm / ProfileForm)** — all three are plain
   `JFrame`s fixed at **1280x720**, dark-themed, and show your brain logo
   (`resources/logo.png`) both as the window icon (taskbar) and as a banner
   image inside the card. They call the controllers above and only display
   success/error messages — no data logic lives inside the GUI classes.

**Flow:** `Main` → `LoginForm` → (register link) → `RegisterForm` → back to
`LoginForm` → successful login → `ProfileForm` → logout → back to `LoginForm`.

---

## 📤 Uploading to GitHub

```bash
cd AuthModule
git init
git add .
git commit -m "Member 1: User Authentication & Profile module"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```

If this is being merged into the team's shared repository, just copy the
`src/model`, `src/controller`, `src/gui`, and `src/util` folders (and
`resources/logo.png`) into the shared project's matching folders, and make
sure package names (`model`, `controller`, `gui`, `util`) don't collide with
teammates' classes — if they do, ask the team to agree on one shared package
structure before merging.

---

## ⚠️ Notes for the demo
- `users.dat` will be created automatically the first time someone registers —
  don't worry if it's not in the repo yet, add it to `.gitignore` since it's
  runtime data, not source code.
- Password rule: minimum 6 characters. Username rule: minimum 4 characters.
- This uses no external libraries — pure Java + Java Swing, so it will run on
  any machine with JDK 8+ installed.
