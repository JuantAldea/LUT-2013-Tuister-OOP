package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "publish")
@XmlAccessorType(XmlAccessType.NONE)
public class PublishPDU extends PDU {

    @XmlAttribute(name = "text")
    protected String text;

    @SuppressWarnings("unused")
    private PublishPDU() {
    }

    public PublishPDU(String text) {
        this.text = text;
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static PublishPDU XMLParse(String xml) throws JAXBException {
        return (PublishPDU) PDU.XMLParse(xml, PublishPDU.class);
    }
}
