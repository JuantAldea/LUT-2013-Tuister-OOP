package common;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class User {
    
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
    
    public String toString(){
        return "[username: " + this.username +", password: " + this.password + "]";
    }
}
