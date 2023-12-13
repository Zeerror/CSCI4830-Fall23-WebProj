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

        String keyword = request.getParameter("keyword");
        StringBuilder searchResults = new StringBuilder();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            String selectSQL = "SELECT * FROM users WHERE username LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, "%" + keyword + "%");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String userEmail = rs.getString("email");
                String role = rs.getString("role");

                searchResults.append("USER ID: ").append(userId).append(", ")
                        .append("USERNAME: ").append(username).append(", ")
                        .append("USER EMAIL: ").append(userEmail).append(", ")
                        .append("ROLE: ").append(role).append("<br>");
            }

            // HTML content to be sent as a response
            String htmlResponse = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "    <meta charset=\"UTF-8\">" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "    <title>Search Results</title>" +
                    "    <style>" +
                    "                html {height: 100%;}\r\n"
                    + "        body {\r\n"
                    + "            font-family: Arial, sans-serif;\r\n"
                    + "            background-image: url(\"https://drive.google.com/uc?export=view&id=1CiuAGBxH0eygLTTMeNvUMK8rlrT3QfPT\");\r\n"
                    + "            background-size: 100%;\r\n"
                    + "            background-color: grey;\r\n"
                    + "            background-position: center;\r\n"
                    + "            text-align: center;\r\n"
                    + "            background-blend-mode: soft-light;\r\n"
                    + "            height: 100%;\r\n"
                    + "            margin: 0px;\r\n"
                    + "        }\r\n"
                    + "        h2 {\r\n"
                    + "            font-size: 42px;\r\n"
                    + "            color: rgb(255, 255, 255);\r\n"
                    + "            text-align: center;\r\n"
                    + "            text-shadow: black 0px 0px 3px;\r\n"
                    + "        }\r\n"
                    + "        .container {\r\n"
                    + "            width: 100%;\r\n"
                    + "            margin: auto;\r\n"
                    + "            overflow: hidden;\r\n"
                    + "        }\r\n"
                    + "        header {\r\n"
                    + "            margin: 0px;\r\n"
                    + "            background: #ffffff;\r\n"
                    + "            color: #333;\r\n"
                    + "            padding-top: 12px;\r\n"
                    + "            min-height: 50px;\r\n"
                    + "            border-bottom: #e8491d 3px solid;\r\n"
                    + "        }\r\n"
                    + "        header a {\r\n"
                    + "            color: #333;\r\n"
                    + "            text-decoration: none;\r\n"
                    + "            text-transform: uppercase;\r\n"
                    + "            font-size: 16px;\r\n"
                    + "            margin-right: 20px;\r\n"
                    + "        }\r\n"
                    + "        header ul {\r\n"
                    + "            margin: 0;\r\n"
                    + "            padding: 0;\r\n"
                    + "            list-style: none;\r\n"
                    + "            overflow: hidden;\r\n"
                    + "        }\r\n"
                    + "        header li {\r\n"
                    + "            float: right;\r\n"
                    + "            padding-right: 24px;\r\n"
                    + "               margin-left: -8px;\r\n"
                    + "            display: inline;\r\n"
                    + "            padding: 0 20px 0 20px;\r\n"
                    + "        }\r\n"
                    + "        header #branding {\r\n"
                    + "            float: left;\r\n"
                    + "            padding-left: 24px;\r\n"
                    + "        }\r\n"
                    + "        header #branding h1 {\r\n"
                    + "            margin: 0;\r\n"
                    + "        }\r\n"
                    + "        header nav {\r\n"
                    + "            float: right;\r\n"
                    + "            margin-top: 10px;\r\n"
                    + "        }\r\n"
                    + "        header .highlight {\r\n"
                    + "            color: #e8491d;\r\n"
                    + "            font-weight: bold;\r\n"
                    + "        }\r\n"
                    + "        header a:hover {\r\n"
                    + "            color: #e8491d;\r\n"
                    + "            font-weight: bold;\r\n"
                    + "        }\r\n"
                    + "        form {\r\n"
                    + "            position: relative;\r\n"
                    + "            min-width: 250px;\r\n"
                    + "            min-height: 300px;\r\n"
                    + "            width: 20%;\r\n"
                    + "            height: 45%;\r\n"
                    + "            margin: auto;\r\n"
                    + "            background-color: white;\r\n"
                    + "            padding: 20px;\r\n"
                    + "            border: 2px solid #e8491d;\r\n"
                    + "            border-radius: 5px;\r\n"
                    + "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        h3 {\r\n"
                    + "            font-family: Arial, sans-serif;\r\n"
                    + "            text-align: left;\r\n"
                    + "            display: block;\r\n"
                    + "            padding-left: 10px;\r\n"
                    + "            margin-bottom: 10px;\r\n"
                    + "            margin-top: 20px;\r\n"
                    + "            text-align: bold;\r\n"
                    + "            color: #333;\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        input[type=\"text\"],\r\n"
                    + "        input[type=\"password\"] {\r\n"
                    + "            width: 90%;\r\n"
                    + "            padding: 10px; /* Adjust the padding as needed */\r\n"
                    + "            margin: auto;\r\n"
                    + "            margin-bottom: 10px;\r\n"
                    + "            font-size: 14px;\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        input[type=\"submit\"] {\r\n"
                    + "            position: absolute;\r\n"
                    + "            right: 12px;\r\n"
                    + "            bottom: 12px;\r\n"
                    + "            background-color: #e8491d;\r\n"
                    + "            color: white;\r\n"
                    + "            width: 150px;\r\n"
                    + "            padding: 10px 30px;\r\n"
                    + "            border: none;\r\n"
                    + "            border-radius: 5px;\r\n"
                    + "            cursor: pointer;\r\n"
                    + "            font-size: 14px;\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        input[type=\"submit\"]:hover {\r\n"
                    + "            background-color: rgb(240, 138, 108);\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        div {\r\n"
                    + "            position: static;\r\n"
                    + "            bottom: 0;\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        p.error-message {\r\n"
                    + "            color: red;\r\n"
                    + "        }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <header>" +
                    "                <div class=\"container\">\r\n"
                    + "            <div id=\"branding\">\r\n"
                    + "                <h1><span class=\"highlight\">The Leftovers - Search Results</span></h1>\r\n"
                    + "            </div>\r\n"
                    + "            <nav>\r\n"
                    + "                <ul>\r\n"
                    + "                    <li><a href=\"landingPage.html\">Home</a></li>\r\n"
                    + "                    <li><a href=\"/webproject/simpleInsertHB.html\">Make Reservation</a></li>\r\n"
                    + "                </ul>\r\n"
                    + "            </nav>\r\n"
                    + "        </div>" +
                    "    </header>" +
                    "    <h2>Search Results</h2>" +
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
