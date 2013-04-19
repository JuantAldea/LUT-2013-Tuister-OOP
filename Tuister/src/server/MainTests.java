package server;

import java.util.Date;

import common.LoginPDU;
import common.PostPDU;
import common.RegisterPDU;
import common.User;

public class MainTests {

    public static void main(String[] args) throws Exception {
        User user = new User("asd", "dsa");
        String xml = user.toXML();

        User user3 = (User) User.XMLParse(xml);

        System.out.println(user3.toString());
        System.out.println(xml);
        RegisterPDU registerPDU = new RegisterPDU(user3);
        System.out.println(registerPDU.toXML());
        LoginPDU login = new LoginPDU("usuario", "contraseña");
        System.out.println(login.toXML());

        PostPDU postPDU = new PostPDU("Necesitamos mas minerales", "autor", new Date(), new Integer(123));
        System.out.println(postPDU.toXML());
    }
}
