package server;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        System.out.println("----------------------Server---------------");
        Server server = new Server();
        ServerState state = ServerState.getInstance();
        state.setListeningPort(27015);
        state.startServer();
        Thread serverThread = new Thread(server);
        serverThread.start();
        serverThread.join();
    }
}
