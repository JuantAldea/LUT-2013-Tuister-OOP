package common;

import java.util.Date;

public class Post {
    protected String text;
    protected String author;
    protected Date date;
    protected Integer id;
    protected Integer likes;

    @SuppressWarnings("unused")
    private Post() {
    }

    public Post(String text, String author, Integer likes, Date date, Integer id) {
        this.text = text;
        this.author = author;
        this.likes = likes;
        this.date = new Date();
        this.id = id;
    }

    public String toString() {
        return "[Text: " + this.text + " Author: " + this.author + " Likes: " + likes + " Date: " + this.date + " ID: " + this.id + "]";
    }
}