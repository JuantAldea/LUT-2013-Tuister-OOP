package server;

public class ServerMain {

    public static void main(String[] args) throws Exception {
        Server srv = new Server();
        ServerState state = ServerState.getInstance();
        state.startServer();
        Thread serverThread = new Thread(srv);
        serverThread.start();
        serverThread.join();
    }
}
