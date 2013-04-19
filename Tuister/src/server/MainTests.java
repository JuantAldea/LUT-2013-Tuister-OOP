package server;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import common.User;

public class MainTests {

    public static void main(String[] args) throws Exception {
        StringWriter writer = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(User.class);
        Marshaller m = context.createMarshaller();
        User user = new User("asd", "dsa");
        m.marshal(user, writer);   
        Unmarshaller um = context.createUnmarshaller();
        User user2 = (User)um.unmarshal(new StringReader(writer.toString()));
        
        System.out.println(user2.toString());
    }
}