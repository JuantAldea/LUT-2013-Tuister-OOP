package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "like")
@XmlAccessorType(XmlAccessType.NONE)
public class LikePDU extends PDU {
    @XmlAttribute(name = "postid")
    protected Integer postid;

    @SuppressWarnings("unused")
    private LikePDU() {
    }

    public LikePDU(Integer postid) {
        this.postid = postid;
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static LikePDU XMLParse(String xml) throws JAXBException {
        return (LikePDU) PDU.XMLParse(xml, LikePDU.class);
    }
}
