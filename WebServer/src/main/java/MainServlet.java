import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "main", urlPatterns = "/main")
public class MainServlet extends HttpServlet {
    private static ArrayList<Table> tables = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            tables = UtilitiesDB.selectTable();
            req.setAttribute("users", tables);
            req.getRequestDispatcher("main.ftl").forward(req, resp);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkParams(String id_chat, String user_id, String cnt_slave, String cnt_master, String bucks, String name) {
        return null != id_chat && null != user_id && null != cnt_slave && null != cnt_master && null != bucks && null != name &&
                !id_chat.isEmpty() && !user_id.isEmpty() && !cnt_slave.isEmpty() && !cnt_master.isEmpty() && !bucks.isEmpty() && !name.isEmpty();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id_chat = request.getParameter("id_chat");
        String user_id = request.getParameter("user_id");
        String cnt_slave = request.getParameter("cnt_slave");
        String cnt_master = request.getParameter("cnt_master");
        String bucks = request.getParameter("bucks");
        String name = request.getParameter("name");
        if (checkParams(id_chat, user_id, cnt_slave, cnt_master, bucks, name)) {
            synchronized (tables) {
                try {
                    UtilitiesDB.insertTable(new Table(user_id, id_chat, cnt_slave, cnt_master, bucks, name));
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (null != user_id && !user_id.isEmpty() && !checkParams(id_chat, user_id, cnt_slave, cnt_master, bucks, name)) {
            try {
                UtilitiesDB.deleteFromTables(user_id);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        doGet(request, response);
    }
}
