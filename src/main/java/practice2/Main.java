package practice2;

import java.sql.*;
import java.util.Scanner;
import java.sql.ResultSet;

/**
 * Предметная область: Мессенджер
 * Table Participants
 * CREATE TABLE IF NOT EXISTS participants
 * (ID SERIAL PRIMARY KEY  NOT NULL,
 * id_chat         INT     NOT NULL,
 * user_id         INT     NOT NULL,
 * count_slave     INT     DEFAULT 0,
 * count_master    INT     DEFAULT 0,
 * bucks           INT     DEFAULT 300,
 * FOREIGN  KEY (user_id)
 * REFERENCES names (user_id)
 * );
 * Table names
 * CREATE TABLE IF NOT EXISTS names
 * (ID SERIAL PRIMARY KEY NOT NULL,
 * user_id          INT     UNIQUE,
 * name             TEXT    UNIQUE
 * );
 */
public class Main {
    private static final String url = "jdbc:postgresql://localhost:5432/vk_chats";
    private static final String user = "postgres";
    private static final String password = "1";

    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    public static void insertInOneTable(Connection connection, Scanner in) {
        System.out.println("Введите запись в первую таблицу через запятую: id_chat, count_slave, count_master, bucks");
        var str = in.next();
        var temp = str.split(",");
        var id_chat = temp[0];
        var count_slave = temp[1];
        var count_master = temp[2];
        var bucks = temp[3];

        var id = selectIdFromTable(connection, "participants") + 1;

        try (Statement stmt = connection.createStatement()) {
            var sqlCommand = "Select * from names";
            ResultSet rs = stmt.executeQuery(sqlCommand);
            System.out.println("В таблице names находятся данные: ");
            while (rs.next()) {
                var user_id = rs.getString("user_id");
                var name = rs.getString("name");
                var id_n = rs.getString("id");
                System.out.println(id_n + ", user_id=" + user_id + ", name=" + name);
            }
            System.out.println("Выберите user_id с которым вы хотите связать новую запись");
            var user_id = in.next();
            sqlCommand = "INSERT INTO public.participants\n" + "(id, id_chat, user_id, count_slave, count_master, bucks)\n"
                    + "VALUES(" + id + ", " + id_chat + ",  " + user_id + ", " + count_slave + ", " + count_master + "," + bucks + ");\n";
            stmt.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int selectIdFromTable(Connection connection, String tableName) {
        String query = "select max(id) from " + tableName + "";
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

    public static void deleteFromOneTable(Connection connection) throws SQLException {
        System.out.println("Выберите id из первой таблицы чтобы удалить данные");
        printTable(connection, "participants");
        var id = new Scanner(System.in).next();
        printTable(connection, "names");
        String query = "Delete from participants where id=?";
        try {
            var stmt = connection.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(id));
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteFromTwoTable(Connection connection, Scanner in) throws SQLException {
        printTable(connection, "participants");
        System.out.println("Выберите ключ user_id из первой таблицы, чтобы удалить даннные из второй таблицы:");
        var user_id = in.next();

        System.out.println("Хотите ли вы удалить связанные данные с первой таблицей? н\\д");
        var approval = in.next();

        if (approval.toLowerCase().equals("н")) {
            return;
        } else {
            try (var stmt = connection.prepareStatement("delete from names where user_id=" + user_id)) {
                var stm = connection.prepareStatement("delete from participants where user_id=" + user_id);
                stm.execute();
                System.out.println("Удаление из первой таблицы прошло успешно.");
                printTable(connection, "participants");
                stmt.execute();
                System.out.println("Удаление из второй таблицы прошло успешно.");
                printTable(connection, "names");


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void insertSecondTable(Connection connection, String user_id, String name) {
        var id = selectIdFromTable(connection, "names") + 1;

        String sqlCommand = "insert into names(id, user_id, \"name\") values(" + id + ", " + user_id + ", '" + name + "');";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlCommand);
            System.out.println("Данные в таблице обновились");
            printTable(connection, "names");
        } catch (SQLException e) {
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

    public static void selectNaturalJoin(Connection connection) {
        String query = "select * from participants natural join names;";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String id_chat = rs.getString("id_chat");
                String cnt_slave = rs.getString("count_slave");
                String cnt_master = rs.getString("count_master");
                String bucks = rs.getString("bucks");
                String name = rs.getString("name");
                System.out.println(name + ", user_id=" + user_id + ", id_chat=" + id_chat + ", cnt_slave=" + cnt_slave + ", cnt_master=" + cnt_master + ", bucks=" + bucks);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        conn = connect();

        while (true) {
            System.out.println("Выберите номер задания: 1, 2, 3, 4, 5");
            var command = new Scanner(System.in).next();
            try {
                switch (command) {
                    case "1":
                        selectNaturalJoin(conn);
                        continue;
                    case "2":
                        System.out.println("Введите user_id: ");
                        var user_id = new Scanner(System.in).next();
                        System.out.println("Введите name: ");
                        var name = new Scanner(System.in).next();
                        insertSecondTable(conn, user_id, name);
                        continue;
                    case "3":
                        insertInOneTable(conn, new Scanner(System.in));
                        continue;
                    case "4":
                        deleteFromTwoTable(conn, new Scanner(System.in));
                        continue;
                    case "5":
                        deleteFromOneTable(conn);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}