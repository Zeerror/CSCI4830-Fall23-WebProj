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

@WebServlet("/SimpleSearchHB")
public class SimpleSearch extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static String url = "jdbc:mysql://ec2-3-21-27-66.us-east-2.compute.amazonaws.com:3306/myDB";
    static String user = "hramirez_remote";
    static String password = "csci4830";
    static Connection connection = null;

    public SimpleSearch() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String keyword = SimpleLogin.username;
        StringBuilder searchResults = new StringBuilder();
        
        System.out.println("USERNAME: " + keyword);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            String selectSQL = "SELECT * FROM reservations WHERE customerUsername = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, keyword);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int reservationId = rs.getInt("reservationID");
                String date = rs.getString("reservationDate");
                String time = rs.getString("reservationTime");
                String guests = rs.getString("numberOfGuests");

                searchResults.append("<div class=\"card\"><div class=\"text\">")
                .append("Date: ").append(date).append(" | ")
                .append("Time: ").append(time).append(" | ")
                .append("Number of Guests: ").append(guests)
                .append("<form action=\"DeleteReservations\" method=\"POST\">")
                .append("<input type=\"hidden\" name=\"reservationID\" value=\"" + reservationId + "\">")
                .append("<input class=\"li\" type=\"submit\" value=\"DELETE\"></form>")
                .append("</div></div><br>");
            }

            // HTML content to be sent as a response
            String htmlResponse = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "    <meta charset=\"UTF-8\">" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "    <title>Search Results</title>" +
                    "    <style>" +
                    "                html {height: 100%;}"
                    + "        body {"
                    + "            font-family: Arial, sans-serif;"
                    + "            background-image: url(\"https://drive.google.com/uc?export=view&id=1CiuAGBxH0eygLTTMeNvUMK8rlrT3QfPT\");"
                    + "            background-size: 100%;"
                    + "            background-color: grey;"
                    + "            background-position: center;"
                    + "            text-align: center;"
                    + "            background-blend-mode: soft-light;"
                    + "            height: 100%;"
                    + "            margin: 0px;"
                    + "        }"
                    + "        h2 {"
                    + "            font-size: 42px;"
                    + "            color: rgb(255, 255, 255);"
                    + "            text-align: center;"
                    + "            text-shadow: black 0px 0px 3px;"
                    + "        }"
                    + "        .container {"
                    + "            width: 100%;"
                    + "            margin: auto;"
                    + "            overflow: hidden;"
                    + "        }"
                    + "        header {"
                    + "            margin: 0px;"
                    + "            background: #ffffff;"
                    + "            color: #333;"
                    + "            padding-top: 12px;"
                    + "            min-height: 50px;"
                    + "            border-bottom: #e8491d 3px solid;"
                    + "        }"
                    + "        header a {"
                    + "            color: #333;"
                    + "            text-decoration: none;"
                    + "            text-transform: uppercase;"
                    + "            font-size: 16px;"
                    + "            margin-right: 20px;"
                    + "        }"
                    + "        header ul {"
                    + "            margin: 0;"
                    + "            padding: 0;"
                    + "            list-style: none;"
                    + "            overflow: hidden;"
                    + "        }"
                    + "        header li {"
                    + "            float: right;"
                    + "            padding-right: 24px;"
                    + "               margin-left: -8px;"
                    + "            display: inline;"
                    + "            padding: 0 20px 0 20px;"
                    + "        }"
                    + "        header #branding {"
                    + "            float: left;"
                    + "            padding-left: 24px;"
                    + "        }"
                    + "        header #branding h1 {"
                    + "            margin: 0;"
                    + "        }"
                    + "        header nav {"
                    + "            float: right;"
                    + "            margin-top: 10px;"
                    + "        }"
                    + "        header .highlight {"
                    + "            color: #e8491d;"
                    + "            font-weight: bold;"
                    + "        }"
                    + "        header a:hover {"
                    + "            color: #e8491d;"
                    + "            font-weight: bold;"
                    + "        }"
                    + "        form {"
                    + "            position: relative;"
                    + "			   right: 5px;"
                    + "            margin-left: auto;"
                    + "			   margin-right: 8px;"
                    + "            padding: 0px !important;"
                    + "			   color: red;"
                    + "        }"
                + "				.li {"
                    + "				float: right;"
                    + "				background-color: transparent;"
                    + "				border: none;"
                    + "				font-size: 16px;"
                    + "				color: red;"
                    + "			}"
                    + ""
                    + "        h3 {"
                    + "            font-family: Arial, sans-serif;"
                    + "            text-align: left;"
                    + "            display: block;"
                    + "            padding-left: 10px;"
                    + "            margin-bottom: 10px;"
                    + "            margin-top: 20px;"
                    + "            text-align: bold;"
                    + "            color: #333;"
                    + "        }"
                    + ""
                    + "        input[type=\"text\"],"
                    + "        input[type=\"password\"] {"
                    + "            width: 90%;"
                    + "            padding: 10px; /* Adjust the padding as needed */"
                    + "            margin: auto;"
                    + "            margin-bottom: 10px;"
                    + "            font-size: 14px;"
                    + "        }"
                    + ""
                    + "			input[type=\"submit\"] {"
                    + "				color; red;"
                    + "			}"
                    + "        input[type=\"submit\"]:hover {"
                    + "            color: pink;"
                    + "        }"
                    + ""
                    + "        div {"
                    + "            position: static;"
                    + "            bottom: 0;"
                    + "        }"
                    + ""
                    + "        .text {"
                    + "             margin: auto;"
                    + "				margin-left: 8px;"
                    + "             height: 100%;"
                    + "				display: flex;"
                    + "				justify-content: center;"
                    + "				align-items: center;"
                    + "				font-size: 24px;"
                    + "        }"
                    + ""
                    + "		   .card {"
                    + "			    background-color: white;"
                    + "				text-justify: center;"
                    + "				vertical-align: center;"
                    + "				margin: auto;"
                    + "				height: 60px;"
                    + "				border-radius: 5px;"
                    + "				width: 65%;"		
                    + "				min-width: 450px;"
                    + "	            border: 2px solid #e8491d;"
                    + "		   }"	
                    + "        p.error-message {"
                    + "            color: red;"
                    + "        }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <header>" +
                    "                <div class=\"container\">"
                    + "            <div id=\"branding\">"
                    + "                <h1><span class=\"highlight\">The Leftovers - My Reservations</span></h1>"
                    + "            </div>"
                    + "            <nav>"
                    + "                <ul>"
                    + "                    <li><a href=\"dashboard.html\">Home</a></li>"
                    + "                    <li><a href=\"/webproject/makeAReservation.html\">Make Reservation</a></li>"
                    + "                </ul>"
                    + "            </nav>"
                    + "        </div>" +
                    "    </header>" +
                    "    <h2>My Reservations</h2>" +
                    "    <div id=\"searchResults\">" +
                    "        <div class=\"result\">" + searchResults.toString() + "</div>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            // Set content type and write the HTML response to the HttpServletResponse
            out.println(htmlResponse);

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
