# Mapua Sim: The Quadsem Survival Game ☕

Mapua Sim is a turn-based resource-management and survival game developed in Java. The game simulates the fast-paced, high-pressure environment of Mapua University's 10-week Quarter-Semester (quadsem) system. Players must balance their academic responsibilities, mental health, and physical well-being through random, database-driven weekly events.

## Core Mechanics
To win (pass the semester), players must survive 10 turns (weeks) by keeping three critical stats below failing thresholds:
*   **Assignments (< 15):** Your academic workload.
*   **Sleep Debt (< 30):** Your physical exhaustion.
*   **Stress (< 20):** Your mental health. If this maxes out, you suffer a breakdown, forcing you to skip a turn.

## Features
*   **10-Turn Survival Loop:** A rigid time-management system mirroring the Mapua quadsem.
*   **Dynamic Event Engine:** Pulls from a MySQL database of 40 unique events.
*   **Conditional Choices:** Available dialogue options change based on your current stats.
*   **Save/Load System:** Database integration allows players to save their progress mid-semester.
*   **Graphical User Interface:** Built natively using Java Swing.

## Tech Stack
*   **Language:** Java (JDK 17+)
*   **GUI Framework:** Java Swing (Developed via Apache NetBeans)
*   **Database:** MySQL (Hosted locally via XAMPP)
*   **Database Driver:** JDBC (`mysql-connector-java.jar`)

## How to Run Locally
1. Clone this repository to your local machine.
2. Start **Apache** and **MySQL** via the XAMPP Control Panel.
3. Open `phpMyAdmin` and create a database named `mapua_sim_db`.
4. Import the included `mapua_sim_db.sql` file to generate the events and save tables.
5. Open the project in Apache NetBeans.
6. Ensure the `mysql-connector-java.jar` driver is added to the project libraries.
7. Run the `Main.java` file to start the game.

## Developers
*   Sebastian Nicolas Cambusa
*   [Groupmate Name/Role]
*   [Groupmate Name/Role]
*   [Groupmate Name/Role]
