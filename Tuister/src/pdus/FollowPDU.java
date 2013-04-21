package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "follow")
@XmlAccessorType(XmlAccessType.NONE)
public class FollowPDU extends PDU {
    @XmlAttribute(name = "username")
    protected String username;

    @SuppressWarnings("unused")
    private FollowPDU() {
    }

    public FollowPDU(String username) {
        this.username = username;
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static FollowPDU XMLParse(String xml) throws JAXBException {
        return (FollowPDU) PDU.XMLParse(xml, FollowPDU.class);
    }
}
