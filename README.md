Campus Equipment Reservation System



Project Description

The Campus Equipment Reservation System is a Java-based web application developed to allow university students and faculty members to submit equipment reservation requests.



Users can select the equipment category, specify the quantity and booking duration, and view their booking receipt after submitting the form.



Technologies Used

\- Frontend: HTML5 and CSS3

\- Backend: Java Servlets

\- Database: MySQL

\- Database Connectivity: JDBC

\- Server: Apache Tomcat

\- Development Environment: Apache NetBeans



Main Features

\- Equipment reservation form for students and faculty.

\- Equipment categories: Laptop, Projector, and Study Pod.

\- Automatic calculation of booking costs.

\- 15% discount for faculty members.

\- Input validation and error handling.

\- Storage of reservation records in the MySQL database.

\- Booking receipt displaying reservation details and total cost.



Database

The project uses a database named campus\_facility\_db with a reservations table.



The Database SQL Package folder contains:

\- schema.sql: Creates the database and reservations table.

\- data.sql: Inserts sample reservation records.



How to Run the Project

1\. Open the project in Apache NetBeans.

2\. Make sure MySQL/MariaDB is running.

3\. Create the database by executing schema.sql.

4\. Ensure the MySQL JDBC driver dependency is available through Maven.

5\. Configure the database connection details in ReservationServlet.java if necessary.

6\. Run the project using Apache Tomcat.

7\. Open the booking form and submit a reservation.



Project Structure

\- src: Java Servlet source code.

\- Web Pages: HTML booking form and web resources.

\- Database SQL Package: SQL database scripts.



Academic Project

Developed as part of a university Web Technologies group assignment.

