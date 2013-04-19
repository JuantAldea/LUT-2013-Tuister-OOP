package server;

import java.util.Date;

import common.LoginPDU;
import common.PostPDU;
import common.PublishPDU;
import common.RegisterPDU;

public class MainTests {

    public static void main(String[] args) throws Exception {
        LoginPDU login = new LoginPDU("usuario", "contraseña");
        System.out.println(login.toXML());
        RegisterPDU register = new RegisterPDU("usuario2", "contraseña2");
        System.out.println(register.toXML());
        
        PostPDU postPDU = new PostPDU("Necesitamos mas minerales", "autor", new Integer(1337), new Date(), new Integer(42));
        System.out.println(postPDU.toXML());
        
        PublishPDU publish = new PublishPDU("Crear mas superamos");
        System.out.println(publish.toXML());
    }
}
