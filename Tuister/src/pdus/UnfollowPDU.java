package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "unfollow")
@XmlAccessorType(XmlAccessType.NONE)
public class UnfollowPDU extends PDU {

    @XmlAttribute(name = "username")
    protected String username;

    @SuppressWarnings("unused")
    private UnfollowPDU() {
    }

    public UnfollowPDU(String username) {
        this.username = username;
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static UnfollowPDU XMLParse(String xml) throws JAXBException {
        return (UnfollowPDU) XMLParse(xml, UnfollowPDU.class);
    }
}
