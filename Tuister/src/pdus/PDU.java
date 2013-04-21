package pdus;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/*
 * Base class of all the PDUs (Protocol Data Units), it uses JAXB for marshalling and unmarshalling Java objects as XML
 * This approach helps easily extend the number of PDUs and the contents of them since the only change needed is adding
 * new fields to the object with the proper JAXB decorator.
 */

abstract public class PDU {
    protected String toXML(@SuppressWarnings("rawtypes") Class classObject) throws JAXBException {
        JAXBContext jaxbcontext = JAXBContext.newInstance(classObject);
        Marshaller marshaller = jaxbcontext.createMarshaller();
        StringWriter stringwriter = new StringWriter();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.marshal(this, stringwriter);
        String xml = stringwriter.toString();
        stringwriter.flush();
        return xml;
    }

    protected static PDU XMLParse(String xml, @SuppressWarnings("rawtypes") Class classObject) throws JAXBException {
        JAXBContext jaxbcontext = JAXBContext.newInstance(classObject);
        StringReader stringreader = new StringReader(xml);
        Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
        return (PDU) unmarshaller.unmarshal(stringreader);
    }
}
