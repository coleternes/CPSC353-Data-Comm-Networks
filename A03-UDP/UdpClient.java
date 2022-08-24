import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
*  UDP Client Program
*  Connects to a UDP Server
*  A while-loop allows the user to send messages over and over to a server
*  until the user disconnects by typing exit.
*/


class UdpClient {
  public static void main(String[] args) throws Exception {
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    DatagramSocket clientSocket = new DatagramSocket();
    InetAddress ipAddress = InetAddress.getByName("localhost");

    // Declaration of variables to be used in while-loop
    String userMessage = "";
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];
    String serverResponse = "";

    // While-loop to continue to prompt user until they type "Goodbye"
    while (true) {
      System.out.print("[Client] ");
      userMessage = inFromUser.readLine(); // Message fom the user to the server

      if (userMessage.toLowerCase().equals("exit")) {
        System.out.println("[Client] You disconnected");
        break; // Breaks from server when user types "Goodbye"
      }

      sendData = userMessage.getBytes();

      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 9876);

      clientSocket.send(sendPacket);

      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

      clientSocket.receive(receivePacket);

      serverResponse = new String(receivePacket.getData());
      System.out.println("[Server] " + serverResponse);
    }

    clientSocket.close();
  }
}
