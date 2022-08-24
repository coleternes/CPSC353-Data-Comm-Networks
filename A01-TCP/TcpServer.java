import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
*  TCP Server Program.
*  Listens on a TCP port
*  Receives a line of input from a TCP client
*  Returns an upper case version of the line to the client
*/

class TcpServer {
  public static void main(String[] argv) throws Exception {
    String welcomeMessage = "[Server] Welcome. Please type a sentence.\n";
    String clientMsg, serverMsg;

    ServerSocket welcomeSocket = null;

    try {
      welcomeSocket = new ServerSocket(6789);
    } catch (Exception e) {
      System.out.println("[Server] Failed to open socket connection");
      System.exit(0);
    }

    while (true) {
      System.out.println("[Server] Waiting for Client to connect.");
      Socket connectionSocket = welcomeSocket.accept();
      BufferedReader inFromClient = new BufferedReader(
          new InputStreamReader(connectionSocket.getInputStream()));
      DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream());
      outToClient.writeBytes(welcomeMessage);
      System.out.println("[Server] Welcome message sent.");

      while (true) {
        clientMsg = inFromClient.readLine();

        if (clientMsg.toLowerCase().equals("exit")) {
          System.out.println("[Server] Client disconnected\n");
          break;
        }

        System.out.println("[Client] " + clientMsg);
        serverMsg = clientMsg.toUpperCase();
        System.out.println("[Server] " + serverMsg);
        outToClient.writeBytes(serverMsg + '\n');
      }

      connectionSocket.close();
    }
  }
}
