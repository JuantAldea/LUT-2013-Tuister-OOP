package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "userlistrequest")
@XmlAccessorType(XmlAccessType.NONE)
public class UserListRequestPDU extends PDU {

    public UserListRequestPDU() {
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static UserListRequestPDU XMLParse(String xml) throws JAXBException {
        return (UserListRequestPDU) PDU.XMLParse(xml, UserListRequestPDU.class);
    }

}
