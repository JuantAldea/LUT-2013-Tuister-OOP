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
                statement.executeUpdate(String.format("insert into users(username, password) values(\"%s\", \"%s\")", username,
                        password));
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
            ResultSet rs = statement.executeQuery(String.format("select id from users where username = \"%s\" AND password=\"%s\"",
                    username, password));
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
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            int result = statement.executeUpdate(String
                    .format("insert into posts(author, body) values (\"%d\", \"%s\")", userID, text));
            if (result == 1) {
                System.out.println("TODO BIEN");
            } else {
                // errror
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void like(Integer userID, Integer postID) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(String.format("select * from likes where user=\"%d\" and post=\"%s\"", userID,
                    postID));
            if (!rs.next()) {
                int result = statement.executeUpdate(String.format("insert into likes(user, post) values (\"%d\", \"%s\")", userID,
                        postID));
                if (result == 1) {
                    System.out.println("TODO BIEN");
                }
                rs = statement.executeQuery(String.format("select likes from posts where id=\"%d\"", postID));
                rs.next();
                result = statement.executeUpdate(String.format("update posts set likes=%d where id =%d", rs.getInt("likes") + 1,
                        postID));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void unLike(Integer userID, Integer postID) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(String.format("select * from likes where user=\"%d\" and post=\"%s\"", userID,
                    postID));
            if (rs.next()) {
                int result = statement.executeUpdate(String.format("delete from likes where user=\"%d\" and post =\"%d\"", userID,
                        postID));
                if (result == 1) {
                    System.out.println("TODO BIEN");
                }
                rs = statement.executeQuery(String.format("select likes from posts where id=\"%d\"", postID));
                rs.next();
                result = statement.executeUpdate(String.format("update posts set likes=%d where id =%d", rs.getInt("likes") - 1,
                        postID));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void follow(Integer userID, String userToFollow) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(String.format("select id from users where username=\"%s\"", userToFollow));
            // valid user
            if (rs.next()) {
                Integer userToFollowID = rs.getInt("id");
                rs = statement.executeQuery(String.format("select * from followers where follower=%d and followed=%d", userID,
                        userToFollowID));
                // not following already
                if (!rs.next()) {
                    int result = statement.executeUpdate(String.format("insert into followers(follower, followed) values(%d, %d)",
                            userID, userToFollowID));
                    // send ack
                } else {
                    // already following
                }
            } else {
                // error user invalid
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void unFollow(Integer userID, String userToFollow) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(String.format("select id from users where username=\"%s\"", userToFollow));
            // valid user
            if (rs.next()) {
                Integer userToFollowID = rs.getInt("id");
                if (userID != userToFollowID) {
                    rs = statement.executeQuery(String.format("select * from followers where follower=%d and followed=%d", userID,
                            userToFollowID));
                    // not following already
                    if (rs.next()) {
                        int result = statement.executeUpdate(String.format("delete from followers where follower=%d and followed=%d",
                                userID, userToFollowID));
                        // send ack
                    } else {
                        // already following
                    }
                } else {
                    // cannot follow yourself
                }
            } else {
                // error user invalid
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void followingUsersRequest(Integer userID) {

    }

    public void userListRequest() {

    }

    public void userContentRequest(String username) {

    }

}
