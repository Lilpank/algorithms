import java.sql.*;
import java.util.ArrayList;

public class UtilitiesDB {
    private static final String url = "jdbc:postgresql://localhost:5432/vk_chats";
    private static final String user = "postgres";
    private static final String password = "1";

    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    public static void printTable(Connection conn, String tableName) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from " + tableName);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        System.out.println("Таблица " + tableName);
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
    }

    public static int selectIdFromTable(Connection connection, String tableName) {
        String query = "select max(id) from " + tableName + ";";
        var maxId = 0;
        try (Statement stmt = connection.createStatement()) {
            var rs = stmt.executeQuery(query);

            while (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return maxId;
    }

    public static void insertTable(Table table) throws SQLException, ClassNotFoundException {
        Connection connection = connect();
        var name_id = selectIdFromTable(connection, "names") + 1;
        var participant_id = selectIdFromTable(connection, "participants") + 1;

        String sqlInsertIntoParticipant = "insert into participants(id, id_chat, user_id, count_slave, count_master, bucks)" +
                "values(" + participant_id + ", " + Integer.parseInt(table.getId_chat()) + ", "
                + Integer.parseInt(table.getUser_id()) + ", " + Integer.parseInt(table.getCount_slave()) + ", " + Integer.parseInt(table.getCount_master()) + ", " + Integer.parseInt(table.getBucks()) + ");";
        var sqlInsertIntoNames = "insert into names(id, user_id, \"name\") values(" + name_id + ", " + Integer.parseInt(table.getUser_id()) + ", '" + table.getName() + "');";
        System.out.println(sqlInsertIntoNames);
        System.out.println(sqlInsertIntoParticipant);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlInsertIntoNames);
            stmt.executeUpdate(sqlInsertIntoParticipant);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Table> selectTable() throws SQLException, ClassNotFoundException {
        ArrayList<Table> tables = new ArrayList<>();
        Connection con = connect();

        String query = "select * from participants, names where participants.user_id  = names.user_id;";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String id_chat = rs.getString("id_chat");
                String cnt_slave = rs.getString("count_slave");
                String cnt_master = rs.getString("count_master");
                String bucks = rs.getString("bucks");
                String name = rs.getString("name");
                tables.add(new Table(id_chat, user_id, cnt_slave, cnt_master, bucks, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }

    public static void deleteFromTables(String user_id) throws SQLException, ClassNotFoundException {
        Connection connection = connect();

        try (var stmt = connection.prepareStatement("delete from names where user_id=" + user_id)) {
            var stm = connection.prepareStatement("delete from participants where user_id=" + user_id);
            stm.execute();
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
