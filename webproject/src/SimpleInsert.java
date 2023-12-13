import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class SimpleInsert
 */
@WebServlet("/SimpleInsertHB")
public class SimpleInsert extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SimpleInsert() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name").trim();
        int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
        String dateString = request.getParameter("date").trim();
        String timeString = request.getParameter("time").trim();
        Connection connection = null;
        String insertSql = "INSERT INTO reservations (CustomerUsername, date, time, tableID) VALUES (?, ?, ?, ?)";

        try {
            DBConnection.getDBConnection();
            connection = DBConnection.connection;

            // Parsing date and time strings into java.sql.Date and java.sql.Time
            Date date = Date.valueOf(dateString);
            Time time = Time.valueOf(timeString);

            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, date);
            preparedStatement.setTime(3, time);
            preparedStatement.setInt(4, tableNumber);
            preparedStatement.execute();

            response.getWriter().println("Reservation successful");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}