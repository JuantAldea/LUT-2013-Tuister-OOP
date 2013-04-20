package server;

import java.util.Date;

import pdus.ErrorPDU;
import pdus.FollowPDU;
import pdus.FollowingUsersRequestPDU;
import pdus.LikePDU;
import pdus.LoginPDU;
import pdus.LogoutPDU;
import pdus.PostPDU;
import pdus.PublishPDU;
import pdus.RegisterPDU;
import pdus.UnfollowPDU;
import pdus.UnlikePDU;
import pdus.UserContentRequestPDU;
import pdus.UserListRequestPDU;


public class MainTests {

    public static void main(String[] args) throws Exception {
        LoginPDU login = new LoginPDU("Manolito", "contraseña");
        System.out.println(login.toXML());
        RegisterPDU register = new RegisterPDU("Manolito", "contraseña2");
        System.out.println(register.toXML());

        PublishPDU publish = new PublishPDU("Crear mas superamos");
        System.out.println(publish.toXML());

        LogoutPDU logout = new LogoutPDU();
        System.out.println(logout.toXML());

        LikePDU like = new LikePDU(new Integer(1337));
        System.out.println(like.toXML());
        UnlikePDU unlike = new UnlikePDU(new Integer(1337));
        System.out.println(unlike.toXML());

        FollowPDU follow = new FollowPDU("Manolito");
        System.out.println(follow.toXML());
        UnfollowPDU unfollow = new UnfollowPDU("Manolito");
        System.out.println(unfollow.toXML());

        FollowingUsersRequestPDU followingUsersRequest = new FollowingUsersRequestPDU();
        System.out.println(followingUsersRequest.toXML());

        UserListRequestPDU userListRequestPDU = new UserListRequestPDU();
        System.out.println(userListRequestPDU.toXML());

        UserContentRequestPDU userContentRequestPDU = new UserContentRequestPDU("Manolito");
        System.out.println(userContentRequestPDU.toXML());

        System.out.println("----------------------Server---------------");

        PostPDU postPDU = new PostPDU("Necesitamos mas minerales", "Manolito", new Integer(1337), new Date(),
                new Integer(42));
        System.out.println(postPDU.toXML());
        ErrorPDU error = new ErrorPDU("No puedo contruir ahi");
        System.out.println(error.toXML());
    }
}
