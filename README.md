## 🇬🇧 English

# 📚 BookShelf Backend: Manage Your Personal Library with Spring Boot

Welcome to the back-end repository of **BookShelf**, a web application designed to help you manage your personal library. Users can add, modify, and remove books from their library, and keep track of the number of times each book has been read. Utilizing Spring Boot for the backend and PostgreSQL as the database, BookShelf offers an intuitive user experience and advanced features to organize your book collection.

## Project Description

BookShelf is a web application designed to:

1. **Add, Modify, and Remove Books**: Easily manage your library by adding, updating, or deleting books.
2. **Track Reading Progress**: Keep track of the number of times each book has been read.
3. **Generate PDF of Your Library**: Download a PDF of your updated book list, including any modifications or deletions.

## Main Features

- **Library Management**: Add, update, and delete books in your personal library.
- **Reading Tracking**: Track the number of times you have read each book.
- **PDF Generation**: Generate a PDF of your book list.

## Technologies Used

- **Backend**: Spring Boot 🍃
- **Database**: PostgreSQL 🐘
- **Language**: Java ☕️

## Project Structure

- **src/main/java**: Contains the application's source code.
- **src/main/resources**: Contains application resources, such as configuration files and static assets.
- **src/test/java**: Contains unit and integration tests for the application.

## Frontend Repository

This repository contains only the backend part of the application. You can find the frontend repository here:

👉 [BookShelf Frontend](https://github.com/alicelazzeri/BookShelf-FRONTEND) 👈

## API and Swagger Documentation

This repository contains the backend that provides APIs for the front-end application.
- **Swagger API Docs**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Installation and Configuration

### Prerequisites

- JDK 11 or higher
- Maven 3.6.3 or higher
- PostgreSQL

### Installation Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/alicelazzeri/BookShelf-BACKEND.git
   ```
2. Navigate to the project directory:
   ```bash
   BookShelf-BACKEND
   ```
3. Configure the PostgreSQL database:
   - Create a database named `book_shelf`.
   - Update the database credentials in the `src/main/resources/application.properties` file.

4. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. Access the application:
   Open your browser and go to `http://localhost:8080`.

## Contributing to the Application

Contributions and pull requests are welcome! Feel free to explore the open issues and contribute with improvements or bug fixes.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Contact

For questions or to report issues, you can open an issue on GitHub.
