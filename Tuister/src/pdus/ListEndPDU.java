package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "list_end")
@XmlAccessorType(XmlAccessType.NONE)
public class ListEndPDU extends PDU {

    public ListEndPDU() {
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static ListEndPDU XMLParse(String xml) throws JAXBException {
        return (ListEndPDU) PDU.XMLParse(xml, LogoutPDU.class);
    }
}
