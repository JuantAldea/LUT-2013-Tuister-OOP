package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "unlike")
@XmlAccessorType(XmlAccessType.NONE)
public class UnlikePDU extends PDU {
    @XmlAttribute(name = "postid")
    protected Integer postid;

    @SuppressWarnings("unused")
    private UnlikePDU() {
    }

    public UnlikePDU(Integer postid) {
        this.postid = postid;
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static UnlikePDU XMLParse(String xml) throws JAXBException {
        return (UnlikePDU) PDU.XMLParse(xml, UnlikePDU.class);
    }

}
