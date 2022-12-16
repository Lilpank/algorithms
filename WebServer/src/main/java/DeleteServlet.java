import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "delete", urlPatterns = "/delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            if (request.getParameter("id1") != null) {
                UtilitiesDB.deleteFromTables(request.getParameter("id1"), "participants");
            } else if (request.getParameter("id2") != null) {
                UtilitiesDB.deleteFromTables(request.getParameter("id2"), "names");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            request.setAttribute("tableWithJoin", UtilitiesDB.selectTable("participants, names", "where participants.user_id  = names.user_id;"));
            request.setAttribute("table1", UtilitiesDB.selectTable("participants", ""));
            request.setAttribute("table2", UtilitiesDB.selectTable("names", ""));
            request.getRequestDispatcher("main.ftl").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
