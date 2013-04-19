package server;
import java.util.Hashtable;
import java.util.LinkedHashSet;

import common.User;


public class UserPosts {
    protected Hashtable<User, LinkedHashSet<String>> posts;

    public LinkedHashSet<String> getPosts(User user) {
        return this.posts.get(user);
    }

    public boolean addPost(User user, String post) {
        LinkedHashSet<String> userPosts = this.getPosts(user);
        return userPosts != null ? userPosts.add(post) : false;
    }

    public boolean removePost(User user, String post) {
        LinkedHashSet<String> userPosts = this.getPosts(user);
        return userPosts != null ? userPosts.remove(post) : false;
    }
}
