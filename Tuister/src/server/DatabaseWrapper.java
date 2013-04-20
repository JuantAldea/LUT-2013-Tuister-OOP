package server;

public class DatabaseWrapper {
    public boolean registerUser(String user, String password) {
        return true;
    }

    public Integer login(String user, String password) {
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
