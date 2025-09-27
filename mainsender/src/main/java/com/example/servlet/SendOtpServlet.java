package com.example.servlet;
// Standard Java Utilities
import java.io.IOException;
import java.util.Properties;

// *** ALL JAKARTA MAIL IMPORTS ***
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException; // Fixes 'MessagingException cannot be resolved'
import jakarta.mail.PasswordAuthentication; // Fixes 'PasswordAuthentication cannot be resolved'
import jakarta.mail.Session; // Fixes 'Session cannot be resolved'
import jakarta.mail.Transport; // Fixes 'Transport cannot be resolved'
import jakarta.mail.internet.InternetAddress; // Fixes 'InternetAddress cannot be resolved'
import jakarta.mail.internet.MimeMessage;

// Standard Jakarta Servlet Imports
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SendOtpServlet")
public class SendOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000); // 6-digit OTP

        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);

        // Your credentials (must be an App Password)
        final String from = "shreyashnikam2025@gmail.com";
        final String password = "ygymddboaxbdxtic";

        // 1. Setup Mail Properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        // Ensure connection timeout properties are set for robustness
        props.put("mail.smtp.connectiontimeout", "5000"); // 5 seconds
        props.put("mail.smtp.timeout", "5000"); // 5 seconds

        // 2. Create Session with Authenticator (uses jakarta.mail classes)
        Session mailSession = Session.getInstance(props, new Authenticator() {
            // NOTE: PasswordAuthentication must also be imported from jakarta.mail
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // 3. Construct Message
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("OTP AYA HAI");
            message.setText("AAPKA OTP HAI: " + otp);

            // 4. Send Message (uses jakarta.mail.Transport)
            Transport.send(message);

            // Redirect on success
            response.sendRedirect("verify.html");
        } catch (MessagingException e) {
            // Print error to console for debugging
            e.printStackTrace();
            
            // Show a generic error message to the user
            response.setContentType("text/html");
            response.getWriter().println("<h1>Error Sending OTP</h1>");
            response.getWriter().println("<p>An error occurred while sending the email. Please check your console for details.</p>");
            // For Authentication problems:
            if (e.getMessage().toLowerCase().contains("authentication")) {
                 response.getWriter().println("<p style='color:red;'>*Possible Cause:* Authentication Failed. Please verify your Gmail App Password.</p>");
            }
        }
    }
}