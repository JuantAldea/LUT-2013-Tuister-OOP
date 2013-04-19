package server;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class MainTests {

    public static void main(String[] args) throws Exception {
        StringWriter writer = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(User.class);
        Marshaller m = context.createMarshaller();
        User user = new User("asd", "dsa");
        m.marshal(user, writer);
        System.out.println(writer);
    }
}