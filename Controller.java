package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Controller {
    private static DatagramSocket clientSocket;
    private static InetAddress ip;
    public Button send;
    public TextField portNum;
    public TextField ipField;
    public TextField numOne;
    public TextArea resultArea;
    static byte[] buffer = new byte[256];

    public void handleConnectButtonClick() throws IOException {
        clientSocket = new DatagramSocket();
        ip = InetAddress.getLocalHost();
    }

    public void handleSendButtonClick() throws IOException {
        buffer = numOne.getText().getBytes();
        DatagramPacket DpSend = new DatagramPacket(buffer, buffer.length, ip, 1234);
        clientSocket.send(DpSend);
        getText();
    }

    public void getText() throws IOException {
        while (resultArea.getText().trim().length() == 0) {
            DatagramSocket socket = new DatagramSocket(1235);
            DatagramPacket result = new DatagramPacket(buffer, buffer.length);
            socket.receive(result);
            String text = new String(buffer, 0, result.getLength());
            resultArea.setText(text);
        }
    }


}
