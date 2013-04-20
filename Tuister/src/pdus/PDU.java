package pdus;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

abstract public class PDU {

    protected String toXML(@SuppressWarnings("rawtypes") Class classObject) throws JAXBException {
        JAXBContext jaxbcontext = JAXBContext.newInstance(classObject);
        Marshaller marshaller = jaxbcontext.createMarshaller();
        StringWriter stringwriter = new StringWriter();

        // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
        // Boolean.TRUE);
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
