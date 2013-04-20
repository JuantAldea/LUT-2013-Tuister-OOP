package pdus;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.NONE)
public class PostPDU {
    protected static JAXBContext  jaxbcontext  = null;
    protected static Marshaller   marshaller   = null;
    protected static Unmarshaller unmarshaller = null;
    protected static StringWriter stringwriter = null;
    protected static StringReader stringreader = null;

    @XmlAttribute(name = "text")
    protected String              text;
    @XmlAttribute(name = "author")
    protected String              author;
    @XmlAttribute(name = "date")
    protected Date                date;
    @XmlAttribute(name = "likes")
    protected Integer             likes;
    @XmlAttribute(name = "id")
    protected Integer             id;

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
        if (stringwriter == null) {
            stringwriter = new StringWriter();
        }
        if (jaxbcontext == null) {
            jaxbcontext = JAXBContext.newInstance(getClass());
        }

        if (marshaller == null) {
            marshaller = jaxbcontext.createMarshaller();
        }
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.marshal(this, stringwriter);
        String xml = stringwriter.toString();
        stringwriter.flush();
        return xml;
    }

    public static PostPDU XMLParse(String xml) throws JAXBException {
        if (stringreader == null) {
            stringreader = new StringReader(xml);
        }

        if (jaxbcontext == null) {
            jaxbcontext = JAXBContext.newInstance(new Object() {
            }.getClass().getEnclosingClass());
        }

        if (unmarshaller == null) {
            unmarshaller = jaxbcontext.createUnmarshaller();
        }
        return (PostPDU) unmarshaller.unmarshal(stringreader);
    }

}
