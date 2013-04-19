package common;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLSerializable {
    protected static JAXBContext  jaxbcontext  = null;
    protected static Marshaller   marshaller   = null;
    protected static Unmarshaller unmarshaller = null;
    protected static StringWriter stringwriter = null;
    protected static StringReader stringreader = null;

    public String toXML() throws JAXBException {
        if (XMLSerializable.stringwriter == null) {
            XMLSerializable.stringwriter = new StringWriter();
        }
        if (XMLSerializable.jaxbcontext == null) {
            XMLSerializable.jaxbcontext = JAXBContext.newInstance(getClass());
        }

        if (XMLSerializable.marshaller == null) {
            XMLSerializable.marshaller = XMLSerializable.jaxbcontext.createMarshaller();
        }

        XMLSerializable.marshaller.marshal(this, XMLSerializable.stringwriter);
        String xml = XMLSerializable.stringwriter.toString();
        XMLSerializable.stringwriter.flush();
        return xml;
    }

    protected static XMLSerializable XMLParseUser(String xml) throws JAXBException {
        if (XMLSerializable.stringreader == null) {
            XMLSerializable.stringreader = new StringReader(xml);
        }
        
        if (XMLSerializable.jaxbcontext == null) {
            XMLSerializable.jaxbcontext = JAXBContext.newInstance(new Object() {
            }.getClass().getEnclosingClass());
        }
        
        if (XMLSerializable.unmarshaller == null) {
            XMLSerializable.unmarshaller = XMLSerializable.jaxbcontext.createUnmarshaller();
        }
        return (XMLSerializable) XMLSerializable.unmarshaller.unmarshal(XMLSerializable.stringreader);
    }
}
