# Expense Tracker Application

A full-stack expense and income tracking application built with Spring Boot (backend) and React (frontend).

## Features

### Backend Features
- **User Authentication**: Sign up and login with JWT tokens
- **Expense Management**: 
  - Add expenses with description, category, amount, and date
  - Categories: Personal, Survival/Livelihood, Investment
  - Delete expenses
  - Filter expenses by category, date range, and amount
  - Sort expenses by category, amount, or date
- **Income Management**:
  - Add income with description, source, amount, and date
  - Sources: From Investment, Salary, From Trading
  - Delete income
  - Filter income by source, date range, and amount
  - Sort income by source, amount, or date
- **Profit & Loss (P&L)**: Calculate total income, total expenses, and net P&L

### Frontend Features
- Modern React UI with routing
- Login/Signup pages
- Dashboard with overview, expenses, and income tabs
- Add expense/income forms
- Filter and sort panels
- P&L summary display
- Responsive design

## Technology Stack

### Backend
- Java 21
- Spring Boot 4.0.1
- Spring Security with JWT
- PostgreSQL
- Spring Data JPA

### Frontend
- React
- React Router
- Axios
- CSS3

## Setup Instructions

### Prerequisites
- Java 21 or higher
- Maven
- Node.js and npm
- PostgreSQL database

### Backend Setup

1. **Database Setup**:
   - Create a PostgreSQL database named `Learn` (or update `application.properties`)
   - Update database credentials in `src/main/resources/application.properties`

2. **Run Backend**:
   ```bash
   ./mvnw spring-boot:run
   ```
   The backend will start on `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory**:
   ```bash
   cd frontend
   ```

2. **Install dependencies** (if not already installed):
   ```bash
   npm install
   ```

3. **Start the development server**:
   ```bash
   npm start
   ```
   The frontend will start on `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /auth/signup` - User registration
- `POST /auth/signin` - User login

### Expenses
- `GET /expense/user/{userId}` - Get all expenses for a user
- `POST /expense/add` - Add a new expense
- `DELETE /expense/{expenseId}` - Delete an expense
- `GET /expense/filter` - Filter expenses (query params: userId, category, startDate, endDate, minAmount, maxAmount)
- `GET /expense/sort` - Sort expenses (query params: userId, sortBy)
- `GET /expense/total/{userId}` - Get total expenses for a user

### Income
- `GET /income/user/{userId}` - Get all income for a user
- `POST /income/add` - Add a new income
- `DELETE /income/{incomeId}` - Delete an income
- `GET /income/filter` - Filter income (query params: userId, source, startDate, endDate, minAmount, maxAmount)
- `GET /income/sort` - Sort income (query params: userId, sortBy)
- `GET /income/total/{userId}` - Get total income for a user

### P&L
- `GET /pnl/{userId}` - Get profit and loss summary

## Usage

1. **Sign Up**: Create a new account
2. **Login**: Login with your credentials
3. **Add Expenses**: Go to Expenses tab and add your expenses
4. **Add Income**: Go to Income tab and add your income
5. **View Overview**: Check the Overview tab for P&L summary and recent transactions
6. **Filter & Sort**: Use the filter and sort panels to organize your data
7. **Delete**: Click the delete button on any expense or income to remove it

## Project Structure

```
expenses_tracker/
├── src/
│   └── main/
│       ├── java/com/example/expenses_tracker/
│       │   ├── controller/     # REST controllers
│       │   ├── service/        # Business logic
│       │   ├── repository/     # Data access layer
│       │   ├── model/          # Entity models
│       │   ├── dto/            # Data transfer objects
│       │   └── security/       # Security configuration
│       └── resources/
│           └── application.properties
└── frontend/
    └── src/
        ├── components/         # React components
        ├── services/           # API service
        └── App.js              # Main app component
```

## Notes

- The application uses JWT for authentication
- CORS is enabled for `http://localhost:3000`
- Default userId is set to 1 for testing if not logged in
- Make sure PostgreSQL is running before starting the backend
