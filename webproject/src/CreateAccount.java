import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static String url = "jdbc:mysql://ec2-3-21-27-66.us-east-2.compute.amazonaws.com:3306/myDB";
    static String user = "hramirez_remote";
    static String password = "csci4830";

    public CreateAccount() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String newUsername = request.getParameter("newUsername");
        String newEmail = request.getParameter("newEmail");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        //Confirm that passwords match
        if (!newPassword.equals(confirmPassword)) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h3>Passwords do not match!</h3>");
            return;
        }

        Connection connection = null;
        String insertSql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, newPassword);
            preparedStatement.setString(3, newEmail);
            // Default role will be "CUST" as they will be employees
            preparedStatement.setString(4, "CUST"); 

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<h3>Account created successfully!</h3>");
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<h3>Failed to create account!</h3>");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h3>Error occurred while creating the account!</h3>");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
