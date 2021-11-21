package api.utils;

import java.sql.*;

public class DatabaseExecutor {

    private static final String USER = "postgres";
    private static final String PASS = "1";
    private static final String URL = "jdbc:postgresql://localhost:5432/vkapi_db";
    private static Connection connection;

    private DatabaseExecutor() {

    }

    public static void getConnect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String extract(String value) {
        String result = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT token FROM vk")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Cryptor.decryptValue(result);
    }

}
