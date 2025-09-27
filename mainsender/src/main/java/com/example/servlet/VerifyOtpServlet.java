package com.example.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/VerifyOtpServlet") // Yahan bhi mapping ho gayi hai!
public class VerifyOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get OTP entered by user
        String enteredOtp = request.getParameter("otp");

        // Get OTP stored in session
        HttpSession session = request.getSession();
        String generatedOtp = (String) session.getAttribute("otp");

        response.setContentType("text/html");

        if (generatedOtp != null && enteredOtp.equals(generatedOtp)) {
            // Optional: OTP session se hata dein taki koi dobara use na kar sake
            session.removeAttribute("otp"); 
            
            response.getWriter().println("<h2>✅ OTP Verified Successfully!</h2>");
            response.getWriter().println("<a href='index.html'>Go Back</a>");
        } else {
            response.getWriter().println("<h2>❌ Invalid OTP. Try Again.</h2>");
            response.getWriter().println("<a href='verify.html'>Retry</a>");
        }
    }
}