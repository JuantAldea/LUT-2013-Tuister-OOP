package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "ack")
@XmlAccessorType(XmlAccessType.NONE)
public class AckPDU extends PDU {

    public AckPDU() {
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static AckPDU XMLParse(String xml) throws JAXBException {
        return (AckPDU) PDU.XMLParse(xml, AckPDU.class);
    }

}
