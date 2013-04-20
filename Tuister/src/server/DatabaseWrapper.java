package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseWrapper {
    protected static final DatabaseWrapper singletonInstance = new DatabaseWrapper();
    protected Connection connection = null;

    synchronized public static DatabaseWrapper getInstance() {
        return singletonInstance;
    }

    private DatabaseWrapper() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tuister.db");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("DATABASE CONNECTION");
        System.out.println(connection);
    }

    public Integer registerUser(String username, String password) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(String.format("select id from users where username = \"%s\"", username));

            if (!rs.next()) {
                System.out.println("POLLACA");
                statement.executeUpdate(String.format("insert into users(username, password) values(\"%s\", \"%s\")", username, password));
                connection.commit();
                rs = statement.executeQuery(String.format("select id from users where username = \"%s\"", username));
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;

    }

    public Integer login(String username, String password) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(String.format("select id from users where username = \"%s\" AND password=\"%s\"", username, password));
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public void publish(Integer userID, String text) {

    }

    public void like(Integer userID, Integer postID) {

    }

    public void unLike(Integer userID, Integer postID) {

    }

    public void follow(Integer userID, String userToFollow) {

    }

    public void unFollow(Integer userID, String userToFollow) {

    }

    public void followingUsersRequest(Integer userID) {

    }

    public void userListRequest() {

    }

    public void userContentRequest(String username) {

    }

}
