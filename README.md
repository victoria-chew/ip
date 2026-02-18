# Kevin User Guide

Kevin is a simple and intuitive desktop task manager built using **Java** and **JavaFX**.

It helps you manage your tasks efficiently through clean and structured command-based inputs inside a graphical interface.

Kevin supports:
- Creating tasks
- Adding deadlines and events
- Searching tasks
- Marking and deleting tasks
- Automatic saving of data

---

# 🚀 Quick Start

## Requirements
- Java 17 or later

## Running the App

From the project root directory, run:

```
./gradlew run
```

Once the app launches, type commands into the input box.

---

# 📌 Features

---

## 📝 Adding Tasks

### Add a To-Do

Adds a simple task without a date.

```
todo <description>
```

Example: todo read book


---

### Add a Deadline

Adds a task with a deadline.

```
deadline <description> /by <yyyy-mm-dd hhmm>
```

Example: deadline submit report /by 2026-02-20 1800


---

### Add an Event

Adds a task with a start and end time.

```
event <description> /from <start> /to <end>
```

Example: event meeting /from 2026-02-20 1400 /to 2026-02-20 1600

---

## 📋 Viewing Tasks

Displays all tasks currently stored.

```
list
```

---

## 🔎 Finding Tasks

Searches for tasks containing a keyword.

```
find <keyword>
```

Example: find report

---

## ✅ Managing Tasks

### Mark a Task as Completed

```
mark <task number>
```

Example: mark 1

---

### Unmark a Task

```
unmark <task number>
```

Example: unmark 1

---

### Delete a Task

```
delete <task number>
```

Example:
delete 1

---

## 🚪 Exiting Kevin

Closes the application.

```
bye
```

---

# 💾 Data Storage

Kevin automatically saves your tasks locally.

All tasks remain available the next time you launch the application.

---

# 💡 Notes

- Task numbers use **1-based indexing**.
- Use `list` to check task numbers before marking or deleting.
- Date format must follow:

```
yyyy-mm-dd hhmm
```

Example:

```
2026-02-20 1800
```

---

# 🛠 Technical Details

- Language: Java
- UI Framework: JavaFX
- Build Tool: Gradle
- Architecture: Command-based modular design

---

# 👨‍💻 Author

Developed by Victoria as part of CS2103T Software Engineering.
