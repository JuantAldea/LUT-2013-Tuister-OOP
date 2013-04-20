package pdus;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "followingusersrequest")
@XmlAccessorType(XmlAccessType.NONE)
public class FollowingUsersRequestPDU extends PDU {

    public FollowingUsersRequestPDU() {
    }

    public String toXML() throws JAXBException {
        return super.toXML(this.getClass());
    }

    public static FollowingUsersRequestPDU XMLParse(String xml) throws JAXBException {

        return (FollowingUsersRequestPDU) PDU.XMLParse(xml, FollowingUsersRequestPDU.class);
    }

}
