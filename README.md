# CSE 464 – Course Project Part 1 and 2

**Author:** Abdalla Osman  
**Project Title:** Directed Graph Parser & Exporter  
**Semester:** Fall 2025  
**Instructor:** Dr. Robert Atkinson

---

## Overview

This project implements a **Directed Graph Manager and Parser** in Java using the **JGraphT** library.  
It allows users to:

- Parse `.dot` files into graph data structures.
- Manage graph nodes and edges programmatically.
- Export graphs back into `.dot` format for visualization.

This project demonstrates understanding of **object-oriented design**, **graph data modeling**, and **software engineering best practices** using **Maven** for build automation.

---

## ⚙️ Tech Stack

| Tool              | Purpose                       |
| ----------------- | ----------------------------- |
| **Java 17+**      | Core language                 |
| **Maven**         | Build & dependency management |
| **JGraphT 1.5.2** | Graph library                 |
| **VS Code**       | Development environment       |

---

##  Project Structure
```
graphproject/
├── pom.xml
├── README.md
├── src/
│ ├── main/java/edu/asu/cse464/graphproject/
│ │ ├── GraphParser.java
│ │ ├── GraphManager.java
│ │ └── Main.java
│ └── test/java/
└── target/
```

---

##  Key Classes

### `GraphParser.java`
- Imports graph data from `.dot` files.
- Uses **`DOTImporter`** to create vertices and edges.
- Handles exceptions and file validation.

### `GraphManager.java`
- Manages the in-memory directed graph (`DefaultDirectedGraph<String, DefaultEdge>`).
- Exports graphs to `.dot` format using **`DOTExporter`**.
- Provides getter and helper methods for graph manipulation.

### `Main.java`
- Entry point for the project.
- Demonstrates parsing, exporting, and displaying graph information.

---

##  Example Usage

### Compile the Project
```
mvn clean compile
```



## Run the Application
```
mvn exec:java -Dexec.mainClass="edu.asu.cse464.graphproject.Main"
```

## Expected Output
```
Graph successfully parsed!
Graph exported to demo_output.dot
```

## Generate .dot File Output
```
target/demo_output.dot
```

## Testing (Optional)
```
mvn test
```

## Build Artifacts
Maven generates compiled .class files inside:
```
/target/classes/
```

and packaged .jar files inside:

```
/target/
```

## Dependencies
Defined in your pom.xml:
```
<dependency>
  <groupId>org.jgrapht</groupId>
  <artifactId>jgrapht-core</artifactId>
  <version>1.5.2</version>
</dependency>

```

## Author
Abdalla Osman

Computer Science Student, Arizona State University

GitHub: @Abdalla-Osman

