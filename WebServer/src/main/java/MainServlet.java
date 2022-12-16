import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "main", urlPatterns = "/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("tableWithJoin", UtilitiesDB.selectTable("participants, names", "where participants.user_id  = names.user_id;"));
            req.setAttribute("table1", UtilitiesDB.selectTable("participants", ""));
            req.setAttribute("table2", UtilitiesDB.selectTable("names", ""));
            req.getRequestDispatcher("main.ftl").forward(req, resp);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
