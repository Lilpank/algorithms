import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "find", urlPatterns = "/find")
public class FindServlet extends MainServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Table> tables = null;
        request.setCharacterEncoding("UTF-8");
        try {
            tables = request.getParameter("user_id") == null ?
                    UtilitiesDB.findRowsInTable(null, request.getParameter("name")) :
                    UtilitiesDB.findRowsInTable(request.getParameter("user_id"), null);


            if (tables.isEmpty() && request.getParameter("user_id") != null) {
                request.setAttribute("error_data", "Not find rows in table by user-id");
            } else if (tables.isEmpty() && request.getParameter("name") != null) {
                request.setAttribute("error_data", "Not find rows in table by name");
            } else {
                request.setAttribute("tableWithJoin", tables);
            }
            request.setAttribute("table1", UtilitiesDB.selectTable("participants", ""));
            request.setAttribute("table2", UtilitiesDB.selectTable("names", ""));
            request.getRequestDispatcher("main.ftl").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
