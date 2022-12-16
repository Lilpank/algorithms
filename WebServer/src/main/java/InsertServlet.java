import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "insert", urlPatterns = "/insert")
public class InsertServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        var name = request.getParameter("name");
        var user_id = request.getParameter("user_id");

        try {
            if (name != null && user_id != null) {
                UtilitiesDB.insertSecondTable(user_id, name);
            } else {
                String id_chat = request.getParameter("id_chat");
                user_id = request.getParameter("user_id");
                String cnt_slave = request.getParameter("cnt_slave");
                String cnt_master = request.getParameter("cnt_master");
                String bucks = request.getParameter("bucks");

                UtilitiesDB.insertTable(new Table(id_chat, user_id, cnt_slave, cnt_master, bucks, name));
            }
            request.setAttribute("tableWithJoin", UtilitiesDB.selectTable("participants, names", "where participants.user_id  = names.user_id;"));
            request.setAttribute("table1", UtilitiesDB.selectTable("participants", ""));
            request.setAttribute("table2", UtilitiesDB.selectTable("names", ""));
            request.getRequestDispatcher("main.ftl").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
