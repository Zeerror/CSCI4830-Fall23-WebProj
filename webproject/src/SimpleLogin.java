import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SimpleLogin")
public class SimpleLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static String url = "jdbc:mysql://ec2-3-21-27-66.us-east-2.compute.amazonaws.com:3306/myDB";
    static String user = "hramirez_remote";
    static String password = "csci4830";
    static Connection connection = null;

    public SimpleLogin() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String userpassword = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            String selectSQL = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userpassword);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // Authentication successful
                HttpSession session = request.getSession();
                session.setAttribute("username", username); // Store the username in the session
                System.out.println("Authentication Successful!!!!!!!!!!");
                response.sendRedirect("landingPage.html"); // Redirect to landingPage.html or any other page
            } else {
                // Authentication failed
                out.println("<h2>Login Failed. Invalid username or password.</h2>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
