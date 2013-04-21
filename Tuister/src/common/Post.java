package common;


public class Post {
    protected String text;
    protected String author;
    protected String date;
    protected Integer id;
    protected Integer likes;

    @SuppressWarnings("unused")
    private Post() {
    }

    public Post(String text, String author, Integer likes, String date, Integer id) {
        this.text = text;
        this.author = author;
        this.likes = likes;
        this.date = date;
        this.id = id;
    }
    
    public Integer getId(){
    	return this.id;
    }

    public String toString() {
        return this.author + " - " + this.date + " - " + this.likes + (this.likes == 1 ? " like" : " likes") + "\n    " + this.text + "\n";
    }
}