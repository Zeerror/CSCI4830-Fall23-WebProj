import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datamodel.Employee;
import util.Info;
import util.UtilDB;
@WebServlet("/SimpleLogin")
public class SimpleLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Perform authentication (you may need to modify this)
        boolean isAuthenticated = authenticate(username, password);

        if (isAuthenticated) {
            // Set user information in session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Redirect to a secure page after successful login
            response.sendRedirect("/webproject/simpleInsertHB.html");
        } else {
            // Display an error message on the login page
            response.sendRedirect("/webproject/login.html?error=true");
        }
    }

    private boolean authenticate(String username, String password) {
        // Implement your authentication logic here
        // You may use UtilDB or any other method to check credentials
        // For learning purposes, you might hardcode some credentials
        return "admin".equals(username) && "password123".equals(password);
    }
}