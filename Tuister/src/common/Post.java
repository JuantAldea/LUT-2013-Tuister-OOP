package common;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "author", "text", "date"})
public class Post {
    protected static JAXBContext  jaxbcontext  = null;
    protected static Marshaller   marshaller   = null;
    protected static Unmarshaller unmarshaller = null;
    protected static StringWriter stringwriter = null;
    protected static StringReader stringreader = null;

    @XmlElement(name = "text")
    protected String              text;
    @XmlElement(name = "author")
    protected String              author;
    @XmlElement(name = "date")
    protected Date                date;
    @XmlElement(name = "id")
    protected Integer             id;

    @SuppressWarnings("unused")
    private Post() {
    }

    public Post(String author, String text, Integer id) {
        this.text = text;
        this.author = author;
        this.id = id;
        this.date = new Date();
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
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.marshal(this, stringwriter);
        String xml = stringwriter.toString();
        stringwriter.flush();
        return xml;
    }

    public static Post XMLParse(String xml) throws JAXBException {
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
        return (Post) unmarshaller.unmarshal(stringreader);
    }
}