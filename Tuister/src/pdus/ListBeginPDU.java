package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "list_begin")
@XmlAccessorType(XmlAccessType.NONE)
public class ListBeginPDU extends PDU {

    public ListBeginPDU() {
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static ListBeginPDU XMLParse(String xml) throws JAXBException {
        return (ListBeginPDU) PDU.XMLParse(xml, LogoutPDU.class);
    }
}
