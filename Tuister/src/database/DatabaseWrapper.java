package database;

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
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            connection = DriverManager.getConnection("jdbc:sqlite:tuister.db");
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
            // TODO INICIALIZAR LA BASE DE DATOS PARA EL RUSO HIJO DE PUTA
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Connection: " + connection);
    }

    public Integer registerUser(String username, String password) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(String.format("select id from users where username = \"%s\"", username));

            if (!rs.next()) {
                statement.executeUpdate(String.format("insert into users(username, password) values(\"%s\", \"%s\")", username, password));
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
            ResultSet rs = statement.executeQuery(String.format("select id from users where username = \"%s\" AND password=\"%s\"", username,
                    password));
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
            int result = statement.executeUpdate(String.format("insert into posts(author, body) values (\"%d\", \"%s\")", userID, text));
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
            ResultSet rs = statement.executeQuery(String.format("select * from likes where user=\"%d\" and post=\"%s\"", userID, postID));
            if (!rs.next()) {
                int result = statement.executeUpdate(String.format("insert into likes(user, post) values (\"%d\", \"%s\")", userID, postID));
                if (result == 1) {
                    System.out.println("TODO BIEN");
                }
                rs = statement.executeQuery(String.format("select likes from posts where id=\"%d\"", postID));
                rs.next();
                result = statement.executeUpdate(String.format("update posts set likes=%d where id =%d", rs.getInt("likes") + 1, postID));
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
            ResultSet rs = statement.executeQuery(String.format("select * from likes where user=\"%d\" and post=\"%s\"", userID, postID));
            if (rs.next()) {
                int result = statement.executeUpdate(String.format("delete from likes where user=\"%d\" and post =\"%d\"", userID, postID));
                if (result == 1) {
                    System.out.println("TODO BIEN");
                }
                rs = statement.executeQuery(String.format("select likes from posts where id=\"%d\"", postID));
                rs.next();
                result = statement.executeUpdate(String.format("update posts set likes=%d where id =%d", rs.getInt("likes") - 1, postID));
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
                rs = statement.executeQuery(String.format("select * from followers where follower=%d and followed=%d", userID, userToFollowID));
                // not following already
                if (!rs.next()) {
                    int result = statement.executeUpdate(String.format("insert into followers(follower, followed) values(%d, %d)", userID,
                            userToFollowID));
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
                    rs = statement.executeQuery(String.format("select * from followers where follower=%d and followed=%d", userID, userToFollowID));
                    // not following already
                    if (rs.next()) {
                        int result = statement.executeUpdate(String.format("delete from followers where follower=%d and followed=%d", userID,
                                userToFollowID));
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

    public ResultSet followingUsersRequest(Integer userID) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            return statement.executeQuery(String.format("select users.username from users, followers where " + "users.id=followers.followed "
                    + "and " + "followers.follower=%d", userID));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet userListRequest() {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            return statement.executeQuery(String.format("select username from users"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet userContentRequest(String username) {
        Statement statement;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery(String.format("select id from users where username=\"%s\"", username));
            // valid user
            if (rs.next()) {
                rs = statement.executeQuery(String.format("select * from posts where author=%d order by posts.date asc", rs.getInt("id")));
            } else {
                rs = null;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet update(Integer userID) {
        Statement statement;
        ResultSet rs = null;

        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            rs = statement.executeQuery(String.format("select posts.body, posts.likes, users.username, posts.post_date, posts.id from posts, followers, users where "
                    + "(posts.author = 1 or (posts.author = followers.followed and followers.follower = 1)) and users.id = posts.author " +
                    "order by post_date asc", userID,
                    userID));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;
    }
}
