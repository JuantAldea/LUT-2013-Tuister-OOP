package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "logout")
@XmlAccessorType(XmlAccessType.NONE)
public class LogoutPDU extends PDU {

    public LogoutPDU() {
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static LogoutPDU XMLParse(String xml) throws JAXBException {
        return (LogoutPDU) PDU.XMLParse(xml, LogoutPDU.class);
    }
}
