package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "login")
@XmlAccessorType(XmlAccessType.NONE)
public class LoginPDU extends PDU {
    @XmlAttribute(name = "username")
    protected String username;

    @XmlAttribute(name = "password")
    protected String password;

    @SuppressWarnings("unused")
    private LoginPDU() {
    }

    public LoginPDU(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static LoginPDU XMLParse(String xml) throws JAXBException {
        return (LoginPDU) PDU.XMLParse(xml, LoginPDU.class);
    }
}
