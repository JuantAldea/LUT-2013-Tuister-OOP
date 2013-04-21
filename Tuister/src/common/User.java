package common;

public class User {
    protected String username;

    @SuppressWarnings("unused")
    private User() {
    }

    public User(String username) {
        this.username = username;
    }

    public String toString() {
        return this.username;
    }
}