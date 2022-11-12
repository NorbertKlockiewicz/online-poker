package pl.edu.agh.kis.pz1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private int clientId;
    private boolean connectionStable = false;

    public ClientConnection() {
        System.out.println("----Client----");
        try {
            Socket socket = new Socket("localhost", 3000);
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
            clientId = dataIn.readInt();
            System.out.println("Client ID: " + clientId);
            connectionStable = true;

        } catch (IOException e) {
            System.out.println("IOException from ClientConnection Constructor");
        }
    }

    public String listenForServerMessage(){
        try {
            return dataIn.readUTF();
        } catch (IOException e) {
            System.out.println("IOException from listenForServerMessage()");
        }
        return null;
    }

    public boolean isConnectionStable() {
        return connectionStable;
    }

    public int getClientId() {
        return clientId;
    }

    public void postRequest(String request){
        try {
            request = "POST " + request;
            dataOut.writeUTF(request);
        } catch (IOException e) {
            System.out.println("IOException from postRequest()");
        }
    }

    public void getRequest(String request){
        try {
            request = "GET " + request;
            dataOut.writeUTF(request);
        } catch (IOException e) {
            System.out.println("IOException from postRequest()");
        }
    }
}