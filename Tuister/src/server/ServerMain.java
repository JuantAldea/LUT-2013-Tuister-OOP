package server;

public class ServerMain {
    public static void main(Integer port) {
        System.out.println("----------------------Server---------------");
        Server server = new Server();
        ServerState state = ServerState.getInstance();
        state.setListeningPort(port);
        state.startServer();
        Thread serverThread = new Thread(server);
        serverThread.start();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
