package pdus;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "ack")
@XmlAccessorType(XmlAccessType.NONE)
public class AckPDU {
    protected static JAXBContext  jaxbcontext  = null;
    protected static Marshaller   marshaller   = null;
    protected static Unmarshaller unmarshaller = null;
    protected static StringWriter stringwriter = null;
    protected static StringReader stringreader = null;

    public AckPDU() {
    }

    public String toXML() throws JAXBException {
        if (stringwriter == null) {
            stringwriter = new StringWriter();
        }
        if (jaxbcontext == null) {
            jaxbcontext = JAXBContext.newInstance(getClass());
        }

        if (marshaller == null) {
            marshaller = jaxbcontext.createMarshaller();
        }
        // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
        // Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.marshal(this, stringwriter);
        String xml = stringwriter.toString();
        stringwriter.flush();
        return xml;
    }

    public static RegisterPDU XMLParse(String xml) throws JAXBException {
        if (stringreader == null) {
            stringreader = new StringReader(xml);
        }

        if (jaxbcontext == null) {
            jaxbcontext = JAXBContext.newInstance(new Object() {
            }.getClass().getEnclosingClass());
        }

        if (unmarshaller == null) {
            unmarshaller = jaxbcontext.createUnmarshaller();
        }
        return (RegisterPDU) unmarshaller.unmarshal(stringreader);
    }

}
