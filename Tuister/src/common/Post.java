package common;

import java.util.Date;

public class Post {
    protected String  text;
    protected String  author;
    protected Date    date;
    protected Integer id;

    @SuppressWarnings("unused")
    private Post() {
    }

    public Post(String author, String text, Integer id) {
        this.text = text;
        this.author = author;
        this.id = id;
        this.date = new Date();
    }
}