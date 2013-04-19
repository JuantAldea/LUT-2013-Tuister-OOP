package common;

import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.NONE)
public class Post extends XMLSerializable {

    @XmlElement(name = "text")
    protected String text;
    @XmlElement(name = "author")
    protected User   author;
    @XmlElement(name = "date")
    protected Date   date;

    public Post() {
    }

    public Post(User author, String text) {
        this.text = text;
        this.author = author;
        this.date = new Date();
    }

    public static Post XMLParseUser(String xml) throws JAXBException {
        return (Post) XMLSerializable.XMLParseUser(xml);
    }
}