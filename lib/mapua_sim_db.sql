-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Sep 29, 2026 at 12:52 PM
-- Server version: 8.0.44
-- PHP Version: 8.3.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mapua_sim_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `events_pool`
--

CREATE TABLE `events_pool` (
  `event_id` int NOT NULL,
  `event_name` varchar(50) DEFAULT NULL,
  `event_description` text,
  `event_type` varchar(20) DEFAULT NULL,
  `req_stress_limit` int DEFAULT '100',
  `choice_A_text` varchar(100) DEFAULT NULL,
  `choice_A_money_cost` int DEFAULT '0',
  `choice_A_stress_mod` int DEFAULT '0',
  `choice_A_sleep_mod` int DEFAULT '0',
  `choice_A_assign_mod` int DEFAULT '0',
  `choice_A_grade_mod` int DEFAULT '0',
  `choice_A_money_mod` int DEFAULT '0',
  `choice_B_text` varchar(100) DEFAULT NULL,
  `choice_B_money_cost` int DEFAULT '0',
  `choice_B_stress_mod` int DEFAULT '0',
  `choice_B_sleep_mod` int DEFAULT '0',
  `choice_B_assign_mod` int DEFAULT '0',
  `choice_B_grade_mod` int DEFAULT '0',
  `choice_B_money_mod` int DEFAULT '0',
  `req_week_min` int DEFAULT '1',
  `req_week_max` int DEFAULT '10'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `events_pool`
--

INSERT INTO `events_pool` (`event_id`, `event_name`, `event_description`, `event_type`, `req_stress_limit`, `choice_A_text`, `choice_A_money_cost`, `choice_A_stress_mod`, `choice_A_sleep_mod`, `choice_A_assign_mod`, `choice_A_grade_mod`, `choice_A_money_mod`, `choice_B_text`, `choice_B_money_cost`, `choice_B_stress_mod`, `choice_B_sleep_mod`, `choice_B_assign_mod`, `choice_B_grade_mod`, `choice_B_money_mod`, `req_week_min`, `req_week_max`) VALUES
(1, 'Surprise Quiz', 'Your professor drops an unannounced quiz. You barely studied.', 'academic', 100, 'Focus and try your best!', 0, 5, 0, 0, 5, 0, 'Guess everything and sleep.', 0, -5, -5, 0, -10, 0, 1, 10),
(2, 'Friends want to play Valo', 'Your blockmates are begging you to 5-stack in Valorant, but you have a lab report due.', 'social', 100, 'Decline and do the lab report.', 0, 5, 5, -5, 5, 0, 'Play with them and buy pizza!', 300, -10, 5, 5, -5, -300, 1, 10),
(3, 'Heavy Rain / Baha', 'The roads are flooded. Commute is going to be terrible.', 'health', 100, 'Book a Grab (Expensive but fast).', 400, -5, -5, 0, 0, -400, 'Wade through the flood.', 0, 10, 10, 0, 0, 0, 1, 10),
(4, 'The Elevator Line', 'The line for the elevator reaches the main gate. Your class starts in 5 minutes.', 'campus', 100, 'Take the stairs! (Exhausting)', 0, 2, 5, 0, 0, 0, 'Wait in line and be late.', 0, 5, 0, 0, -5, 0, 1, 10),
(5, 'Spaghetti Code', 'It is 2 AM. Your Java project throws 50 errors and will not compile.', 'academic', 100, 'Delete it all and rewrite it.', 0, 5, 10, -5, 10, 0, 'Submit the broken code and pray.', 0, 10, -10, -5, -15, 0, 1, 10),
(6, 'Out of Baon', 'You forgot to withdraw and only have 50 pesos left for the day.', 'financial', 100, 'Skip lunch and study hungry.', 0, 10, 5, -2, 0, 0, 'Borrow money from a friend.', 0, 5, 0, 0, 0, 300, 1, 10),
(7, 'Unexpected Free Cut', 'The professor didn’t show up! You have 3 hours of free time.', 'academic', 100, 'Sleep in the library.', 0, -5, -15, 0, 0, 0, 'Do advanced reading for next week.', 0, 5, 0, -5, 5, 0, 1, 10),
(8, 'The Freeloader', 'Your groupmate hasn’t contributed anything to the final project.', 'social', 100, 'Do their part yourself.', 0, 10, 10, -10, 5, 0, 'Report them to the professor.', 0, 5, 0, 0, 0, 0, 1, 10),
(9, 'LMS Crash', 'Blackboard crashed 10 minutes before the midnight deadline.', 'academic', 100, 'Panic refresh until 3 AM.', 0, 10, 15, -5, 0, 0, 'Sleep and email the prof tomorrow.', 0, 5, -10, 0, -5, 0, 1, 10),
(10, 'Coffee Overdose', 'You drank too much iced coffee and now you have palpitations.', 'health', 100, 'Buy water and rest at the clinic.', 50, -10, -5, 5, 0, -50, 'Power through the lecture.', 0, 10, 0, 0, 5, 0, 1, 10),
(11, 'Birthday Inuman', 'Your blockmates are celebrating a birthday. They want you to pitch in.', 'social', 100, 'Pitch in and join them!', 300, -15, 10, 5, -5, -300, 'Decline and go home to study.', 0, 5, -5, -5, 5, 0, 1, 10),
(12, 'Freezing Classroom', 'The aircon in your lab room is set to freezing. You can barely type.', 'campus', 100, 'Buy a hot coffee to survive.', 100, -5, 0, 0, 5, -100, 'Shiver and lose focus.', 0, 5, 0, 0, -5, 0, 1, 10),
(13, 'Pop-up Seminar', 'The department requires attendance for a weekend IT seminar with an entry fee.', 'academic', 100, 'Pay the fee and attend.', 250, 0, 5, 5, 10, -250, 'Skip it and lose points.', 0, 5, -5, -5, -10, 0, 1, 10),
(14, 'Broken Laptop', 'Your laptop blue-screens right before a major coding assignment is due.', 'tech', 100, 'Rush it to a repair shop.', 1000, -5, 5, 0, 0, -1000, 'Borrow a slow PC from the library.', 0, 15, 10, 5, -5, 0, 1, 10),
(15, 'Library Full', 'You need to study, but there are zero vacant seats in the library.', 'campus', 100, 'Study at a nearby Cafe.', 200, -5, -5, -5, 5, -200, 'Study on the floor in the hallway.', 0, 10, 5, -2, 2, 0, 1, 10),
(16, 'Crush Noticed You', 'Your campus crush complimented your outfit today! You are glowing.', 'social', 100, 'Chat them up! (Lose track of time)', 0, -20, 0, 10, -5, 0, 'Say thanks and rush to class.', 0, -5, 0, -2, 5, 0, 1, 10),
(17, 'LRT-1 Breakdown', 'The LRT stopped working, and you are stranded in Roosevelt.', 'commute', 100, 'Book an Angkas/Joyride immediately.', 250, -5, 0, 0, 0, -250, 'Wait in the massive Jeepney line.', 0, 15, 10, 0, -5, 0, 1, 10),
(18, 'Wrong Upload', 'You just realized you uploaded your rough draft instead of the final paper.', 'academic', 100, 'Beg the professor in person.', 0, 15, 0, 0, 5, 0, 'Accept your fate and take the zero.', 0, 5, 0, -10, -15, 0, 1, 10),
(19, 'Family Dinner', 'Your parents demand you attend a family dinner, but you have assignments piling up.', 'social', 100, 'Attend the dinner (Parents treat you).', 0, -10, -5, 10, 0, 500, 'Make an excuse to stay and code.', 0, 15, 5, -10, 5, 0, 1, 10),
(20, 'Canteen Crowded', 'The canteen is absolutely packed. You only have a 30-minute break.', 'campus', 100, 'Buy fast food outside.', 150, 5, 0, 0, 0, -150, 'Skip eating.', 0, 10, 10, 0, -5, 0, 1, 10),
(21, 'Extension Granted!', 'A strict professor miraculously extended the deadline for the machine problem.', 'academic', 100, 'Use the time to sleep.', 0, -10, -20, 0, 0, 0, 'Use the time to perfect the code.', 0, 5, -5, -10, 15, 0, 1, 10),
(22, 'Eye Strain', 'Staring at NetBeans for 8 hours has given you a massive migraine.', 'health', 100, 'Buy meds and take a nap.', 100, -15, -10, 5, 0, -100, 'Push through the pain.', 0, 15, 10, -5, 5, 0, 1, 10),
(23, 'Intramurals Week', 'It’s Mapua Intramurals! Classes are suspended, but attendance is checked.', 'social', 100, 'Buy merch and watch the games.', 300, -15, -5, 5, 0, -300, 'Sign attendance then sneak out to study.', 0, 10, 0, -10, 10, 0, 1, 10),
(24, 'Lost Mapua ID', 'You lost your ID and the guards won’t let you inside the campus.', 'campus', 100, 'Pay for a replacement immediately.', 300, 5, 0, 0, 0, -300, 'Go home and miss your quiz.', 0, 10, -5, 0, -10, 0, 1, 10),
(25, 'Class Suspended', 'The mayor just suspended classes due to a transport strike!', 'campus', 100, 'Catch up on all your sleep.', 0, -10, -20, 0, 0, 0, 'Grind your machine problems.', 0, 5, -5, -15, 10, 0, 1, 10),
(26, 'Corrupted Flash Drive', 'Your USB got a virus from the library computer. Your presentation is gone.', 'tech', 100, 'Pay a tech shop to recover the files.', 500, -5, 0, 0, 5, -500, 'Rewrite the whole presentation tonight.', 0, 15, 15, 5, -5, 0, 1, 10),
(27, 'Tech Career Fair', 'There is a job fair in the gymnasium with free merch, but you have a lab class.', 'social', 100, 'Skip lab and network with companies.', 0, -5, 0, 5, -10, 0, 'Attend the lab class like a good student.', 0, 5, 5, -5, 10, 0, 1, 10),
(28, 'Brownout', 'The power goes out in your barangay right as you were about to code.', 'health', 100, 'Go to a coffee shop with a generator.', 250, 0, -5, -5, 5, -250, 'Sleep through the brownout.', 0, -10, -15, 10, -5, 0, 1, 10),
(29, 'Unfair Grade', 'You got a 65/100 on an exam you know you aced. The professor made a mistake.', 'academic', 100, 'Respectfully contest the grade.', 0, 10, 0, 0, 15, 0, 'Stay quiet and accept it.', 0, -5, 0, 0, -10, 0, 1, 10),
(30, 'Org Recruitment Week', 'A prestigious tech organization is recruiting. It looks great on a resume.', 'social', 100, 'Pay the membership fee and join.', 150, 5, 5, 5, 5, -150, 'Ignore it. You have no time.', 0, -5, 0, -5, -5, 0, 1, 10),
(31, 'Sick Groupmate', 'Your main programmer caught a fever and can’t finish the backend.', 'academic', 100, 'Take over their coding tasks.', 0, 15, 15, -5, 10, 0, 'Submit an incomplete project.', 0, 5, 0, 0, -15, 0, 1, 10),
(32, 'Freelance Gig', 'Someone offered you a quick web design gig for some cash.', 'financial', 100, 'Take the gig (Sacrifice sleep).', 0, 10, 15, 5, 0, 800, 'Decline and focus on Mapua.', 0, -5, -5, -5, 5, 0, 1, 10),
(33, 'Siomai Rice Craving', 'You are starving and exhausted, but on a tight budget.', 'health', 100, 'Buy Siomai Rice at the corner.', 60, -10, 0, 0, 0, -60, 'Starve until you get home.', 0, 10, 5, 0, 0, 0, 1, 10),
(34, 'Surprise Fire Drill', 'The alarms go off right in the middle of your Calculus exam.', 'campus', 100, 'Use the distraction to review your notes.', 0, -5, 0, 0, 5, 0, 'Buy a snack while waiting outside.', 50, -10, 0, 0, -5, -50, 1, 10),
(35, 'Thesis Defense Dread', 'You witness seniors getting destroyed in their thesis defense.', 'special', 100, 'Use the fear to study harder.', 0, 10, 10, -10, 10, 0, 'Go play video games to cope.', 100, -10, 0, 5, -10, -100, 1, 10),
(36, 'Printer Jam', 'The piso-print shop printer jams 5 minutes before your submission.', 'tech', 100, 'Pay extra to jump the line at another shop.', 100, 5, 0, 0, 5, -100, 'Submit it late.', 0, 15, 0, 0, -10, 0, 1, 10),
(37, 'Upperclassman Advice', 'A senior offers you their old notes and source code for your current subject.', 'social', 100, 'Treat them to lunch to say thanks.', 300, -15, -10, -15, 15, -300, 'Politely decline. You want to learn yourself.', 0, 5, 10, 0, 10, 0, 1, 10),
(38, 'Jeepney Strike', 'There are no jeeps available for your commute home to Pasay.', 'commute', 100, 'Book an expensive Grab.', 450, -5, -5, 0, 0, -450, 'Walk to a different terminal.', 0, 15, 10, 0, 0, 0, 1, 10),
(39, 'Long Weekend', 'It is a special non-working holiday! You have a 3-day weekend.', 'health', 100, 'Do absolutely nothing. Just sleep.', 0, -20, -30, 5, -5, 0, 'Get ahead on all assignments.', 0, 10, 5, -20, 15, 0, 1, 10),
(40, 'The Perfect Cram', 'You crammed for 12 hours straight and miraculously understood everything.', 'academic', 100, 'Take the exam with zero sleep.', 0, 5, 20, -10, 15, 0, 'Buy a massive breakfast to survive.', 250, -5, 10, -10, 15, -250, 1, 10),
(101, 'TBA PROFESSOR', 'You check your schedule and your professor is still listed as TBA. You wait outside the room for 30 minutes, but nobody arrives.', NULL, 0, 'Go Home early', 0, -5, -5, 0, 0, 0, 'Review Syllabus', 0, 2, 0, 0, 5, 0, 1, 2),
(102, 'SECTION DISSOLVED', 'Due to low enrollment, your section was dissolved. You had to petition for a new schedule, resulting in a horrible 5-hour vacant period.', NULL, 0, 'Tambay at Wall', 50, -5, 0, 0, 0, -50, 'Study in Library', 0, 5, 0, 0, 8, 0, 1, 2),
(103, 'COURSE ORIENTATION', 'The professor spends the entire 3-hour period reading the syllabus word-for-word. It is incredibly tedious, but at least there are no assignments yet.', NULL, 0, 'Zone Out', 0, -2, -2, 0, 0, 0, 'Take Detailed Notes', 0, 2, 0, 0, 5, 0, 1, 1),
(104, 'BLACKBOARD CRASH', 'You try to log in to check your initial course requirements, but the Blackboard server is completely down due to heavy first-week traffic.', NULL, 0, 'Panic refresh the page', 0, 5, 0, 0, 0, 0, 'Take a nap instead', 0, -5, -10, 0, 0, 0, 1, 2),
(801, 'FORCED ALL-NIGHTER', 'Your Java code threw a massive NullPointerException at 2 AM. You have no choice but to stay awake and fix it.', NULL, 0, 'Drink Coffee & Suffer', 0, 5, 10, -2, 2, 0, '', 0, 0, 0, 0, 0, 0, 1, 10),
(802, 'UNEXPECTED JAM SESSION', 'You found an empty room on campus with a decent piano. You spend an hour just playing \"Let It Be\", completely forgetting about your deadlines. A massive weight lifts off your shoulders.', NULL, 10, 'Take a breath', 0, -10, -5, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, 1, 10),
(904, 'SUMMATIVE EXAM', 'It is exam week! A massive summative test is in front of you. Do you rely on stock knowledge, or pull an all-nighter cramming?', NULL, 0, 'Cram All Night', 0, 5, 8, -2, 10, 0, 'Stock Knowledge', 0, -2, 0, 0, -10, 0, 1, 10),
(999, 'HELL WEEK', 'It is Week 10. Thesis defense, final machine problems, and 3 exams are all due at the same time. Survive.', 'special', 100, 'Cram EVERYTHING (Drink 3 Coffees).', 350, 15, 20, -15, 10, -350, 'Prioritize sleep and just pass what you have.', 0, -10, -15, 5, -15, 0, 1, 10);

-- --------------------------------------------------------

--
-- Table structure for table `player_saves`
--

CREATE TABLE `player_saves` (
  `id` int NOT NULL,
  `week` int DEFAULT NULL,
  `turn_in_week` int DEFAULT NULL,
  `money` int DEFAULT NULL,
  `stress` int DEFAULT NULL,
  `sleep_debt` int DEFAULT NULL,
  `assignments` int DEFAULT NULL,
  `academic_points` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `events_pool`
--
ALTER TABLE `events_pool`
  ADD PRIMARY KEY (`event_id`);

--
-- Indexes for table `player_saves`
--
ALTER TABLE `player_saves`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
