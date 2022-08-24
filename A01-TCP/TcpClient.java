import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
/**
*  TCP Client Program
*  Connects to a TCP Server
*  Receives a line of input from the keyboard and sends it to the server
*  Receives a response from the server and displays it.
*/

class TcpClient {
  public static void main(String[] argv) throws Exception {
    String clientMsg, serverMsg;

    Socket clientSocket = null;

    try {
      clientSocket = new Socket("localhost", 6789);
    } catch (Exception e) {
      System.out.println("[Client] Failed to open socket connection");
      System.exit(0);
    }

    BufferedReader inFromServer =  new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
    String welcomeMessage = inFromServer.readLine();
    System.out.println(welcomeMessage);

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

    while (true) {
      System.out.print("[Client] ");
      clientMsg = inFromUser.readLine();

      outToServer.writeBytes(clientMsg + '\n');

      if (clientMsg.toLowerCase().equals("exit")) {
        System.out.println("[Client] You disconnected\n");
        break;
      }
      else {
        serverMsg = inFromServer.readLine();
        System.out.println("[Server] " + serverMsg);
      }
    }

    clientSocket.close();
  }
}
