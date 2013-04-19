package server;

import common.User;

public class MainTests {

    public static void main(String[] args) throws Exception {
        User user = new User("asd", "dsa");
        String xml = user.toXML();

        User user3 = (User) User.XMLParseUser(xml);
        System.out.println(user3.toString());
    }
}
