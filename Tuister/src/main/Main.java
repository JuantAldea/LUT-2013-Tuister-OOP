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
        System.out.println("Server mode params: 0 [<port>]");
        System.out.println("Client mode params: 1 [<IP> <port>]");
        System.out.println("Note: if no arguments beside the mode are specified, the default values are:");
        System.out.println("Host: 127.0.0.1 (localhost)    Port: 27015");
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
        if (args.length >= 1 && args[0].equalsIgnoreCase("0")) {
        	
        	Integer port = 27015; // Default port
        	if (args.length >= 2) {
                // server mode
                port = Main.validPort(args[1]);
        	}

            if (port != -1) {
                ServerMain.main(port);
            } else {
                System.out.println("Invalid port");
            }
        } else if (args.length >= 1 && args[0].equalsIgnoreCase("1")) {
        	
        	String host = "127.0.0.1"; // Default localhost
        	Integer port = 27015; // Default port
        	if (args.length >= 3) {
        		host = args[1];
        		if (!isValidIPv4(host)) {
                    System.out.println("Invalid IP");
                    return;
                }
        		
        		port = Main.validPort(args[2]);
        		if (port == -1) {
        			System.out.println("Invalid port");
        			return;
        		}
        	}
        	
        	ClientMain.main(host, port);
            
        } else {
            Main.help();
        }
    }
}
