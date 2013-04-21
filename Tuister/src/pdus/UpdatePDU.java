package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "update")
@XmlAccessorType(XmlAccessType.NONE)
public class UpdatePDU extends PDU {

    public UpdatePDU() {
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static UpdatePDU XMLParse(String xml) throws JAXBException {
        return (UpdatePDU) PDU.XMLParse(xml, UpdatePDU.class);
    }
}
