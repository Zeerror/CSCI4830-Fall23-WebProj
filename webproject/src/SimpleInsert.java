import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SimpleInsertHB")
public class SimpleInsert extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static String url = "jdbc:mysql://ec2-3-21-27-66.us-east-2.compute.amazonaws.com:3306/myDB";
    static String user = "hramirez_remote";
    static String password = "csci4830";
    static Connection connection = null;

    public SimpleInsert() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("customerName").trim();
        int numberOfGuests = Integer.parseInt(request.getParameter("numberOfGuests"));
        String dateString = request.getParameter("date").trim();
        String timeString = request.getParameter("time").trim();

        HttpSession session = request.getSession(false);

        // Check if a user is logged in
        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                Date date = Date.valueOf(dateString);
                Time time = Time.valueOf(timeString);

                String insertSql = "INSERT INTO reservations (customerName, reservationDate, reservationTime, numberOfGuests, customerUsername) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
                // Sets the name to customerName column.
                preparedStatement.setString(1, name);
                preparedStatement.setDate(2, date);
                // Sets the time in the reservationTime column.
                preparedStatement.setTime(3, time);
                // Sets the number of guests in numberOfGuests column.
                preparedStatement.setInt(4, numberOfGuests);
                // Sets the username to customerUsername column.
                preparedStatement.setString(5, username);
                // Updates.
                preparedStatement.executeUpdate();

                response.getWriter().println("Reservation successful");
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Failed to make a reservation");
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("Exception occurred: " + e.getMessage());
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // User is not logged in --> Continue as Guest
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                Date date = Date.valueOf(dateString);
                Time time = Time.valueOf(timeString);

                String insertSql = "INSERT INTO reservations (customerName, reservationDate, reservationTime, numberOfGuests) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
                preparedStatement.setString(1, name);
                preparedStatement.setDate(2, date);
                preparedStatement.setTime(3, time);
                preparedStatement.setInt(4, numberOfGuests);
                preparedStatement.setString(5, "Guest");
                preparedStatement.executeUpdate();

                response.getWriter().println("Guest reservation successful");
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Failed to make a guest reservation");
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("Exception occurred: " + e.getMessage());
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
}
