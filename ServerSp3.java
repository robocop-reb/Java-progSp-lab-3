package sample;

import java.net.*;
import java.io.*;


public class ServerSp3 {

    static int num[] = {0, 0, 0};
    private boolean running;
    static double sum1 = 0;
    static double sum2 = 0;
    static double ssum = 0;
    private static byte[] buf = new byte[256];

    public ServerSp3() {
    }

    public static DatagramSocket startServer() throws SocketException {
        DatagramSocket socket = new DatagramSocket(1234);
        return socket;
    }

    public static String recieveString(DatagramSocket socket) throws IOException {
        DatagramPacket numberString = new DatagramPacket(buf, buf.length);
        socket.receive(numberString);
        String string = new String(buf, 0, numberString.getLength());
        ;
        buf = null;
        return string;
    }

    public static int[] doThing(String numbersSting) {

        String[] numUtf = numbersSting.split(" ");
        for (int i = 0; i < numUtf.length; i++) {
            num[i] = Integer.parseInt(numUtf[i]);
        }
        return num;
    }

    public static void sendPacket(DatagramSocket socket, double sum) throws IOException {
        buf = String.valueOf(sum).getBytes();
        InetAddress ip = InetAddress.getLocalHost();
        DatagramPacket result = new DatagramPacket(buf, buf.length,ip,1235);
        socket.send(result);
    }

    static Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int i = num[0]; i <= num[1]; i++) {
                sum1 += 3 * i / (2 * i + 1);
            }

        }
    });
    static Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int i = num[1]; i <= num[2]; i++) {
                System.out.println(i);
                sum2 += (i * i + 4 * i + 1);
            }

        }
    });


    public static void main(String[] args) throws IOException, InterruptedException {
        DatagramSocket socket = startServer();
        doThing(recieveString(socket));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        ssum = sum1 + sum2;
        System.out.println(ssum);
        sendPacket(socket, ssum);

    }
}