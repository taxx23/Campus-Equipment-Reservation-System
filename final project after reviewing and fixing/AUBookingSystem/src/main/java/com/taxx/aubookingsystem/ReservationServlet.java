package com.taxx.aubookingsystem;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "ReservationServlet", urlPatterns = {"/ReservationServlet"})
public class ReservationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    String userName = request.getParameter("userName");
    String userRole = request.getParameter("userRole");
    String itemCategory = request.getParameter("itemCategory");
    String quantityStr = request.getParameter("quantity");
    String bookingDaysStr = request.getParameter("bookingDays");
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
        
    if (userName == null || userName.trim().isEmpty() ||
        userRole == null || userRole.trim().isEmpty() ||
        itemCategory == null || itemCategory.trim().isEmpty() ||
        quantityStr == null || quantityStr.trim().isEmpty() ||
        bookingDaysStr == null || bookingDaysStr.trim().isEmpty()) {

        out.println("Please fill in all fields.");
        out.println("<br><a href='booking_form.html' style='color: #985c45;'>Try again</a>");
        return;
    }
    
    userName = userName.trim().toUpperCase();
    userRole = userRole.trim();
    itemCategory = itemCategory.trim();

    int quantity;
    int bookingDays;

    try {
        quantity = Integer.parseInt(quantityStr.trim());
        bookingDays = Integer.parseInt(bookingDaysStr.trim());
    } catch (Exception e) {
        out.println("Quantity and booking days must be numbers.");
        out.println("<br><a href='booking_form.html' style='color: #985c45;'>Try again</a>");
        return;
    }
    
    if (bookingDays <= 0 || quantity <= 0) {
        out.println("Booking days and Quantity must be at least one.");
        out.println("<br><a href='booking_form.html' style='color: #985c45;'>Try again</a>");
        return;
    }
    
    if (bookingDays > 90) {
        out.println("Booking days acannot exceed 90 days.");
        out.println("<br><a href='booking_form.html' style='color: #985c45;'>Try again</a>");
        return;
    }
    
    double dailyRate;

    switch (itemCategory) {
        case "Laptop":
            dailyRate = 50;
            break;
        case "Projector":
            dailyRate = 80;
            break;
        case "POD":
            dailyRate = 100;
            break;
        default:
            out.println("Invalid item category.");
            out.println("<br><a href='booking_form.html' style='color: #985c45;'>Try again</a>");
            return;
    }
    
    double subtotal = quantity * bookingDays * dailyRate;
    double netTotal;

    if (userRole.equals("Faculty")) {
        netTotal = subtotal - (subtotal * 0.15);
    }
    else if (userRole.equals("Student")) {
        netTotal = subtotal;
    } 
    else {
        out.println("Invalid user role.");
        out.println("<br><a href='booking_form.html' style='color: #985c45;'>Try again</a>");
        return;
    }
        
    try {
        String url = "jdbc:mysql://localhost:3306/campus_facility_db";
        String username = "root";
        String password = "";
        
        Class.forName("com.mysql.cj.jdbc.Driver"); //loads the MySQL JDBC driver so Java can use it to connect to the database
        
        Connection con = DriverManager.getConnection(url, username, password);

        String sql = "INSERT INTO reservations "
                   + "(user_name, user_role, item_category, quantity, booking_days, daily_rate, subtotal, net_total) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; //PreparedStatement

        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setString(1, userName);
        ps.setString(2, userRole);
        ps.setString(3, itemCategory);
        ps.setInt(4, quantity);
        ps.setInt(5, bookingDays);
        ps.setDouble(6, dailyRate);
        ps.setDouble(7, subtotal);
        ps.setDouble(8, netTotal);
        
        ps.executeUpdate();
        
        out.println("<html>");
        out.println("<head><title>Booking Receipt</title></head>");
        
        out.println("<body style='font-family: Arial; background-color: #f8f1e8; padding: 30px;'>");
        out.println("<div style='width: 350px; margin: 30px auto; padding: 25px; background-color: #fffaf4; border-radius: 10px; box-shadow: 0 3px 10px rgba(101, 75, 60, 0.12);'>");
        out.println("<h1 style='text-align: center; color: #654b3c;'>Booking Receipt</h1>");
        
        out.println("<p><b>Username:</b> " + userName + "</p>");
        out.println("<p><b>Role:</b> " + userRole + "</p>");
        out.println("<p><b>Item Category:</b> " + itemCategory + "</p>");
        out.println("<p><b>Quantity:</b> " + quantity + "</p>");
        out.println("<p><b>Booking Days:</b> " + bookingDays + "</p>");
        out.println("<p><b>Daily Rate (Per One):</b> AED " + dailyRate + "</p>");
        
        out.println("<hr style='border: 1px solid #ddc9b8;'>");

        out.println("<p><b>Subtotal:</b> AED " + subtotal + "</p>");
        
        if (userRole.equals("Faculty")) {
            double discountAmount = subtotal * 0.15;
            out.println("<p><b>Faculty Discount (15%):</b> AED " + discountAmount + "</p>");
        }
        else {
            out.println("<p><b>Faculty Discount:</b> AED 0.00</p>");
        }
        
        out.println("<h3 style='color: #985c45;'>Net Total: AED " + netTotal + "</h3>");
        out.println("<p style='color: #397d1a; text-align: center;'>Reservation made successfully!</p>");
        out.println("<br><a href='booking_form.html' style='color: #985c45;'>Submit Another Request</a>");
        
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
                
        ps.close();
        con.close();

    } catch (Exception e) {
        out.println("Database error: " + e.getMessage());
        out.println("<br><a href='booking_form.html' style='color: #985c45;'>Back to form</a>");
    }
    }
}
