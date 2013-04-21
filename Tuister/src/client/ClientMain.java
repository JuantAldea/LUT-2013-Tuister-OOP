package client;

public class ClientMain {
	public static void main(String host, Integer port) {
		ClientController controller = new ClientController(host, port);
		Thread controllerThread = new Thread(controller);
		Thread guiThread = new Thread(controller.gui);

		controllerThread.start();
		guiThread.start();

		try {
			guiThread.join();
			controllerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
