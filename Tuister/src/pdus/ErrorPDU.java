package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.NONE)
public class ErrorPDU extends PDU {

    @XmlAttribute(name = "reason")
    protected String reason;

    @SuppressWarnings("unused")
    private ErrorPDU() {
    }

    public ErrorPDU(String reason) {
        this.reason = reason;
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static ErrorPDU XMLParse(String xml) throws JAXBException {
        return (ErrorPDU) PDU.XMLParse(xml, ErrorPDU.class);
    }

}
