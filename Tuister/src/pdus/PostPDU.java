package pdus;

import java.util.Date;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.NONE)
public class PostPDU extends PDU {

    @XmlAttribute(name = "text")
    protected String  text;
    @XmlAttribute(name = "author")
    protected String  author;
    @XmlAttribute(name = "date")
    protected Date    date;
    @XmlAttribute(name = "likes")
    protected Integer likes;
    @XmlAttribute(name = "id")
    protected Integer id;

    @SuppressWarnings("unused")
    private PostPDU() {
    }

    public PostPDU(String text, String author, Integer likes, Date date, Integer id) {
        this.text = text;
        this.author = author;
        this.date = date;
        this.id = id;
        this.likes = likes;
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static PostPDU XMLParse(String xml) throws JAXBException {
        return (PostPDU) PDU.XMLParse(xml, PostPDU.class);
    }

}
