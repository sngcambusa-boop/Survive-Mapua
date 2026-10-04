Mapua Sim: The Quadsem Survival Game
Mapua Sim is a Java Swing resource-management game about surviving an 11-week quadsem. Make choices at campus events, manage Baon and hazard stats, and complete the Departmental Exam at the end of Week 11.

Game Flow
Each regular week has three event turns and a ₱500 allowance when the week advances.

Week 4 and Week 8 end with a scheduled summative exam; Week 10 has Hell Week finals.

Week 11 has three regular random events, followed by the Departmental Exam.

Keep assignments below 20, sleep debt below 40, and stress below 30 to avoid game over.

Use the relief shop to spend Baon on reducing hazard stats.

Save and load progress through the MySQL database.

Architecture (Event-Driven & MVC)
This application is built on Event-Driven Programming and the Model-View-Controller (MVC) architectural pattern to ensure scalable, separated code.

Event-Driven Flow: The application does not run on a linear top-to-bottom loop. Once the UI renders, the main thread enters a sleep state, waiting for physical user interaction. When a player clicks a choice, an ActionEvent fires, waking the engine to execute the corresponding listener logic, update the database, and refresh the UI before returning to an idle state.

Model (Data Layer): The Player and GameEvent classes manage the core logic and memory. They store the raw integers (stress, sleep debt, money, week iteration) and strings (scenario text) independently of the visual interface.

View (Presentation Layer): The MainMenu and GameFrame classes use Java Swing components (like JLabel and JProgressBar) and layout managers to dynamically paint the Cardinal Red and Gold user interface based on the Model's current state.

Controller (Logic Layer): DBConnection and the ActionListeners act as the bridge. They capture the View's button clicks, execute the mathematical stat modifications, communicate with the MySQL database to fetch the next valid event, and command the View to repaint itself.

Requirements
Java 17 or later.

MySQL running locally (MAMP for macOS, XAMPP for Windows).

The mapua_sim_db database, with the events_pool and player_saves tables imported.

The database is not embedded in the JAR. You must import the mapua_sim_db.sql file into your local MySQL server before launching the game. The JDBC driver and audio files are bundled and will load automatically.

Database Connection Settings
Due to differences in local hosting environments, two separate executables are maintained in this repository. Ensure you are running the correct version for your operating system so the JDBC driver can authenticate with your local MySQL server:

macOS (MapuaSim_MAC.jar): Configured for MAMP. Connects to localhost:8889 using username root and password root.

Windows (MapuaSim_Windows.jar): Configured for XAMPP. Connects to localhost:3306 using username root with a blank password "".

Run the Exported JAR
macOS
Start MySQL in MAMP and ensure mapua_sim_db is imported.

Verify the server is running on port 8889.

Open Terminal in the folder containing MapuaSim_MAC.jar.

Run:

Bash
java -jar MapuaSim_MAC.jar
Windows
Start Apache and MySQL in the XAMPP Control Panel and ensure mapua_sim_db is imported via phpMyAdmin.

Verify the server is running on port 3306 (the XAMPP default).

Open Command Prompt or PowerShell in the folder containing MapuaSim_Windows.jar.

Run:

PowerShell
java -jar .\MapuaSim_Windows.jar
Run from Source
From the project root, compile the source files into the configured output directory:

Bash
javac --release 17 -d bin src/*.java
Then run the main menu with the MySQL JDBC driver on the classpath:

Bash
java -cp "bin:lib/mysql-connector-j-26.7.0.jar" MainMenu
On Windows, replace the classpath colon (:) with a semicolon (;). Audio files are read from assets/ when running from source.

Build the Runnable JAR
The checked-in MapuaSim_MAC.jar and MapuaSim_Windows.jar are platform-specific runnable exports. After changing source code, rebuild the classes and recreate the JARs so they include the current application code, MySQL driver, and audio resources. Both JAR manifests use MainMenu as their entry point.

Developers
SEBASTIAN NICOLAS CAMBUSA

JULIAN ANDRE MARILLA

TEDDY BALAORO

REYHAN TIMOTHY SO