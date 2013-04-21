package main;

import java.util.regex.Pattern;

import client.ClientMain;

import server.ServerMain;

public class Main {

    private static boolean isValidIPv4(String address) {
        String IPV4_REGEXP = "(0?\\d?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\." + "(0?\\d?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\."
                + "(0?\\d?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\." + "(0?\\d?\\d|1\\d\\d|2[0-4]\\d|25[0-5])$";
        return Pattern.matches(IPV4_REGEXP, address);

    }

    private static void help() {
        System.out.println("Usage:");
        System.out.println("Server mode params: 0 port");
        System.out.println("Client mode params: 1 IP port");
    }

    private static Integer validPort(String port) {
        Integer p = -1;
        try {
            p = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (p >= 0 && p < 65536) {
            return p;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        if (args.length >= 2 && args[0] == "0") {
            // server mode
            Integer port = Main.validPort(args[1]);
            if (port != -1) {
                ServerMain.main(port);
            } else {
                System.out.println("Invalid port");
            }
        } else if (args.length >= 3 && args[0] == "1") {
            // client mode
            if (!isValidIPv4(args[1])) {
                System.out.println("Invalid IP");
                return;
            }

            Integer port = Main.validPort(args[2]);
            if (port != -1) {
                ClientMain.main(args[1], port);
            } else {
                System.out.println("Invalid port");
            }
        } else {
            Main.help();
        }
    }
}
