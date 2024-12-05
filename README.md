# FUD - Macros Tracker

## Project Description
**FUD** is an application dedicated to the personalized tracking of daily macronutrients. Designed to offer an intuitive and highly customizable experience, the application allows users to monitor their diet, customize nutritional plans, and track physical activities.

### Key Features:
- **Personal Data Management**: Input and modification of personal information and nutritional goals.
- **Personalized Meal Plans**: Creation of personalized meal plans with automatic macronutrient calculations.
- **Meal Tracking**: Daily registration of consumed foods, with options to add new foods or recipes.
- **Workout Management**: Monitoring of workout sessions with calorie calculation.
- **Interactive Home Page**: General overview of daily data, including calories, macronutrients, and workouts.

---

## System Architecture
The application follows a modular architecture with separation of concerns:
- **User Interface (View)**: Implemented with Java Swing, providing modern and interactive components.
- **Controller**: Manages interactions between the user interface and the business logic.
- **Business Logic**: Handles the system’s core operations, such as calculations and data management.
- **ORM**: Manages persistence via PostgreSQL and JDBC.

### Design Patterns Used:
- **Data Access Object (DAO)**: For accessing and manipulating data.
- **Factory**: For flexible object creation.
- **Singleton**: Ensures a single instance of specific components.

---

## Database
The application uses PostgreSQL as its database management system. Below are some of the main entities:
- **User**: Information about the user and their goals.
- **Meal**: Registered meals with nutritional information.
- **Recipe**: Personalized recipes.
- **Exercise**: Workouts with calories and duration.
- **Nutritional Info**: Calories, carbohydrates, proteins, and fats for foods and recipes.

Relationships between entities, such as **one-to-many** and **many-to-many**, are mapped using a custom Object-Relational Mapping (ORM).

---

## Design
The user interface is organized into several views:
- **Home Page**: Displays general data and allows adding workout sessions.
- **Profile Page**: Enables modification of personal information and goals.
- **Daily Tracker**: Tracks foods consumed throughout the day.
- **Plan Page**: Allows customization of the meal plan.

### Available Diagrams:
- Use-case diagrams
- Page navigation diagrams
- UML models for domain, business logic, and ORM

---

## Testing
The project uses **JUnit 5** and **Mockito** to ensure code reliability:
- Tests for the main `Engine` class to verify correct interaction between the view and business logic.
- Tests for DAO classes to ensure proper data access.

---

## Technologies Used
- **Language**: Java
- **Framework**: Java Swing (GUI)
- **Database**: PostgreSQL
- **Build Tool**: Apache Maven
- **Testing**: JUnit 5, Mockito

