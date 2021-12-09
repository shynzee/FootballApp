package footballApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connect {

    private static final Statement statement = null;
    private static final ResultSet resultSet = null;

    public static Connection ConnectDb(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/users?serverTimezone=Europe/Moscow&useSSL=false";
            String user = "root";
            String pass = "";
            Connection connect = DriverManager
                    .getConnection(url, user, pass);

            return connect;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}