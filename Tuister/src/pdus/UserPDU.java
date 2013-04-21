package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class UserPDU extends PDU {
    @XmlAttribute(name = "username")
    protected String username;

    @SuppressWarnings("unused")
    private UserPDU() {
    }

    public UserPDU(String username) {
        this.username = username;
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static UserPDU XMLParse(String xml) throws JAXBException {
        return (UserPDU) PDU.XMLParse(xml, UserPDU.class);
    }

}
