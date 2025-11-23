# Event Management Platform

A robust, secure, and user-friendly Event Management Platform built with Spring Boot 3.x and Thymeleaf.

## Features

- **User Authentication**: Register and Login with Role-based access (USER, ADMIN).
- **Event Management**: Create, View, and Manage events.
- **Admin Approval**: Admins can approve or reject events.
- **Registration**: Users can register for events (capacity checks included).
- **QR Code Check-in**: Unique QR codes for registrations and an Admin check-in system.
- **Calendar Integration**: FullCalendar.js integration to view public events.

## Tech Stack

- **Backend**: Java 21, Spring Boot 3.2.0, Spring Data JPA, Spring Security.
- **Database**: H2 (In-memory for development).
- **Frontend**: Thymeleaf, Bootstrap 5, FullCalendar.js.
- **Tools**: Maven, ZXing (QR Code).

## Getting Started

### Prerequisites

- Java 21 SDK installed.
- Maven (optional, if using IDE).

### Running the Application

1. Open the project in your favorite IDE (IntelliJ IDEA, Eclipse, VS Code).
2. Run the `EventPlatformApplication` class.
3. Access the application at `http://localhost:8080`.

### Default Credentials (Seeded Data)

- **Admin User**:
  - Username: `admin`
  - Password: `admin123`

- **Regular User**:
  - Username: `user`
  - Password: `user123`

## Usage

1. **Login** with the default credentials or register a new user.
2. **Create Event**: Go to "Create Event" to submit a new event.
3. **Admin Dashboard**: Log in as Admin to approve pending events and check-in users.
4. **Calendar**: View the "Calendar" tab to see approved public events.
5. **My Events**: View your created events and registered events (with QR codes).

## API Endpoints

- `/api/events/calendar-feed`: JSON feed for FullCalendar.
- `/api/admin/checkin`: POST endpoint for QR code check-in.
