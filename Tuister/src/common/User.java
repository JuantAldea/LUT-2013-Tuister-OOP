package common;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class User extends XMLSerializable {

    @XmlAttribute(name = "username")
    protected String username;

    @XmlAttribute(name = "password")
    protected String password;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toString() {
        return "[username: " + this.username + ", password: " + this.password + "]";
    }

    public static User XMLParseUser(String xml) throws JAXBException {
        return (User) XMLSerializable.XMLParse(xml);
    }
}
