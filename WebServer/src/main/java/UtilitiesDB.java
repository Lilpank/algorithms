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

    static final Connection connection;

    static {
        try {
            connection = connect();
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    public static int selectIdFromTable(String tableName) {
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
        var participant_id = selectIdFromTable("participants") + 1;

        String sqlInsertIntoParticipant = "insert into participants(id, id_chat, user_id, count_slave, count_master, bucks)" + "values(" + participant_id + ", " + Integer.parseInt(table.getId_chat()) + ", " + Integer.parseInt(table.getUser_id()) + ", " + Integer.parseInt(table.getCount_slave()) + ", " + Integer.parseInt(table.getCount_master()) + ", " + Integer.parseInt(table.getBucks()) + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlInsertIntoParticipant);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Table> selectTable(String tableName, String condition) throws SQLException, ClassNotFoundException {
        ArrayList<Table> tables = new ArrayList<>();
        String query = "select * from " + tableName + " " + condition;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            switch (tableName) {
                case "participants":
                    while (rs.next()) {
                        String user_id = rs.getString("user_id");
                        String id_chat = rs.getString("id_chat");
                        String cnt_slave = rs.getString("count_slave");
                        String cnt_master = rs.getString("count_master");
                        String bucks = rs.getString("bucks");
                        String name = "";
                        String id = rs.getString("id");
                        tables.add(new Table(id_chat, user_id, cnt_slave, cnt_master, bucks, id, name));
                    }
                case "names":
                    while (rs.next()) {
                        String user_id = rs.getString("user_id");
                        String name = rs.getString("name");
                        String id = rs.getString("id");
                        tables.add(new Table(id, user_id, name));
                    }
                    break;
                case "participants, names":
                    while (rs.next()) {
                        String user_id = rs.getString("user_id");
                        String id_chat = rs.getString("id_chat");
                        String cnt_slave = rs.getString("count_slave");
                        String cnt_master = rs.getString("count_master");
                        String bucks = rs.getString("bucks");
                        String name = rs.getString("name");
                        tables.add(new Table(id_chat, user_id, cnt_slave, cnt_master, bucks, name));
                    }
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }

    public static ArrayList<Table> findRowsInTable(String userid, String name) throws SQLException, ClassNotFoundException {
        ArrayList<Table> tables = new ArrayList<>();
        var query = "select * from participants, names where participants.user_id  = names.user_id";
        if (userid != null && !userid.isEmpty()) {
            query = query + " and participants.user_id=" + userid;
        } else if (name != null && !name.isEmpty()) {
            query = query + " and names.name='" + name + "'";
        } else {
            return tables;
        }


        try (var stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String id_chat = rs.getString("id_chat");
                String cnt_slave = rs.getString("count_slave");
                String cnt_master = rs.getString("count_master");
                String bucks = rs.getString("bucks");
                String names = rs.getString("name");
                tables.add(new Table(id_chat, user_id, cnt_slave, cnt_master, bucks, names));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }

    public static void deleteFromTables(String user_id, String table) throws SQLException, ClassNotFoundException {
        try (var stmt = connection.prepareStatement("delete from " + table + "  where id=" + user_id)) {
            stmt.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertSecondTable(String user_id, String name) throws SQLException, ClassNotFoundException {
        var id = selectIdFromTable("names") + 1;

        String sqlCommand = "insert into names(id, user_id, \"name\") values(" + id + ", " + user_id + ", '" + name + "');";

        try (var stmt = connection.prepareStatement(sqlCommand)) {
            stmt.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
