package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "user_content_request")
@XmlAccessorType(XmlAccessType.NONE)
public class UserContentRequestPDU extends PDU {
    @XmlAttribute(name = "username")
    protected String username;

    @SuppressWarnings("unused")
    private UserContentRequestPDU() {
    }

    public UserContentRequestPDU(String username) {
        this.username = username;
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static UserContentRequestPDU XMLParse(String xml) throws JAXBException {
        return (UserContentRequestPDU) PDU.XMLParse(xml, UserContentRequestPDU.class);
    }

}
