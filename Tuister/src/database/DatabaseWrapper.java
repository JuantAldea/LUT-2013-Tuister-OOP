package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tuister.db");
            this.initDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void initDatabase() {
        ArrayList<String> schema = new ArrayList<String>();
        schema.add("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, username VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL,"
                + "UNIQUE(username) ON CONFLICT ABORT)");
        schema.add("CREATE TABLE IF NOT EXISTS posts(id INTEGER PRIMARY KEY, body VARCHAR(128) NOT NULL, likes INTEGER NOT NULL DEFAULT 0, "
                + "post_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, author INTEGER, FOREIGN KEY(author) REFERENCES users(id));");
        schema.add("CREATE TABLE IF NOT EXISTS likes(user INTEGER, post INTEGER, FOREIGN KEY(user) REFERENCES users(id),"
                + "FOREIGN KEY(post) REFERENCES post(id), PRIMARY KEY(user, post));");
        schema.add("CREATE TABLE IF NOT EXISTS followers(follower INTEGER, followed INTEGER, FOREIGN KEY(follower) REFERENCES users(id), "
                + "FOREIGN KEY(followed) REFERENCES users(id), PRIMARY KEY(follower, followed) ON CONFLICT IGNORE);");
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            for (int i = 0; i < schema.size(); i++) {
                statement.executeUpdate(schema.get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // check if there is already a user with such username
            ResultSet queryResults = statement.executeQuery(String.format("select id from users where username = \"%s\"", username));
            // results => username already taken
            if (queryResults.next()) {
                return false;
            }
            // !results => register new user
            statement.executeUpdate(String.format("insert into users(username, password) values(\"%s\", \"%s\")", username, password));
            queryResults = statement.executeQuery(String.format("select id from users where username = \"%s\"", username));
            queryResults.next();
            statement.executeUpdate(String.format("insert into followers  values(%d, %d)", queryResults.getInt("id"), queryResults.getInt("id")));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Integer login(String username, String password) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // check if there is a user witch such username and password
            ResultSet queryResults = statement.executeQuery(String.format("select id from users where username = \"%s\" AND password=\"%s\"",
                    username, password));
            // results => valid login data, return the userID
            if (queryResults.next()) {
                return queryResults.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public boolean publish(Integer userID, String text) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(String.format("insert into posts(author, body) values (%d, \"%s\")", userID, text));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean like(Integer userID, Integer postID) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // check if the post exists
            ResultSet querryResults = statement.executeQuery(String.format("select * from posts where id=%d", postID));
            // no results => invalid post
            if (!querryResults.next()) {
                return false;
            }

            // results => user already likes this post
            querryResults = statement.executeQuery(String.format("select * from likes where user=%d and post=%d", userID, postID));
            if (querryResults.next()) {
                return true;
            }

            // no results => it's a new like, like it and update the post's likes count
            statement.executeUpdate(String.format("insert into likes(user, post) values (%d, %d)", userID, postID));
            querryResults = statement.executeQuery(String.format("select likes from posts where id=%d", postID));
            statement.executeUpdate(String.format("update posts set likes=%d where id =%d", querryResults.getInt("likes") + 1, postID));

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean unLike(Integer userID, Integer postID) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // no results => user doesn't like this post
            ResultSet queryResults = statement.executeQuery(String.format("select * from likes where user=\"%d\" and post=\"%s\"", userID, postID));
            if (!queryResults.next()) {
                return true;
            }
            // results => delete the like and decrease post's likes count
            statement.executeUpdate(String.format("delete from likes where user=\"%d\" and post =\"%d\"", userID, postID));
            // get the post
            queryResults = statement.executeQuery(String.format("select likes from posts where id=\"%d\"", postID));
            queryResults.next();
            // decrease the counter
            statement.executeUpdate(String.format("update posts set likes=%d where id =%d", queryResults.getInt("likes") - 1, postID));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean follow(Integer userID, String userToFollow) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // check if target user is valid
            ResultSet queryResults = statement.executeQuery(String.format("select id from users where username=\"%s\"", userToFollow));
            // no results => invalid user
            if (!queryResults.next()) {
                return false;
            }
            // check if user already follows target user
            Integer userToFollowID = queryResults.getInt("id");
            // cannot follow yourself
            if (userID == userToFollowID) {
                return false;
            }
            queryResults = statement.executeQuery(String.format("select * from followers where follower=%d and followed=%d", userID, userToFollowID));
            // results => already following
            if (queryResults.next()) {
                return true;
            }
            statement.executeUpdate(String.format("insert into followers(follower, followed) values(%d, %d)", userID, userToFollowID));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean unFollow(Integer userID, String userToUnFollow) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // check if target user is valid
            ResultSet queryResults = statement.executeQuery(String.format("select id from users where username=\"%s\"", userToUnFollow));
            // no results => invalid target user
            if (!queryResults.next()) {
                return false;
            }
            // get target user ID
            Integer userToUnFollowID = queryResults.getInt("id");
            // cannot unfollow yourself
            if (userID == userToUnFollowID) {
                return false;
            }
            queryResults = statement.executeQuery(String
                    .format("select * from followers where follower=%d and followed=%d", userID, userToUnFollowID));
            // already not following
            if (!queryResults.next()) {
                return true;
            }
            statement.executeUpdate(String.format("delete from followers where follower=%d and followed=%d", userID, userToUnFollowID));

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ResultSet followingUsersRequest(Integer userID) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            return statement.executeQuery(String.format("select users.username from users, followers where " + "users.id=followers.followed "
                    + "and " + "followers.follower=%d", userID));
        } catch (SQLException e) {
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
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet userContentRequest(String username) {
        Statement statement;
        ResultSet queryResults = null;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // check if requested username is valid
            queryResults = statement.executeQuery(String.format("select id from users where username=\"%s\"", username));
            // no results => invalid user
            if (!queryResults.next()) {
                return null;
            }
            // results => get user's posts
            queryResults = statement.executeQuery(String.format("select * from posts where author=%d order by posts.post_date asc",
                    queryResults.getInt("id")));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return queryResults;
    }

    public ResultSet update(Integer userID) {
        Statement statement;
        ResultSet queryResults = null;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // get feed from followed users and user's posts
            queryResults = statement
                    .executeQuery(String
                            .format("select posts.body, posts.likes, users.username, posts.post_date, posts.id "
                                    + "from posts, followers, users where (users.id = posts.author and posts.author = followers.followed and followers.follower = %d) "
                                    + "order by posts.post_date asc", userID, userID));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return queryResults;
    }
}
