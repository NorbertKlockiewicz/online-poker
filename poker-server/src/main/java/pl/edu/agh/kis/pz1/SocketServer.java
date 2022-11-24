package pl.edu.agh.kis.pz1;

import java.net.*;
import java.io.*;

public class SocketServer {
    private ServerSocket serverSocket;
    private int numPlayers;
    private final int maxPlayers;
    private final ServerSideConnection[] connections;
    private final Controller controller;

    public SocketServer(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.connections = new ServerSideConnection[100];
        this.numPlayers = 0;
        this.controller = new Controller(this);
        try {
            serverSocket = new ServerSocket(3000);
        } catch (IOException e) {
            System.out.println("IOException from GameServer Constructor");
        }
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            while(connections.length < 101) {
                Socket socket = serverSocket.accept();
                ServerSideConnection connection = new ServerSideConnection(socket, numPlayers);
                connections[numPlayers] = connection;
                System.out.println("Player# " + numPlayers + " has connected");
                Thread t = new Thread(connection);
                t.start();
                numPlayers++;
            }
        } catch (IOException e) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    public void responseToClient(int clientId, String s){
        try {
            this.connections[clientId].sendResponse(s);
        } catch (Exception e) {
            System.out.println("IOException from responseToClient()");
        }
    }

    private class ServerSideConnection implements Runnable{
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private final int connectionID;

        public ServerSideConnection(Socket socket, int connectionID) {
            this.connectionID = connectionID;
            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("IOException from ServerSideConnection Constructor");
            }
        }

        public void sendResponse(String s){
            try {
                dataOut.writeUTF(s);
            } catch (IOException e) {
                System.out.println("IOException from sendResponse()");
            }
        }

        public void run(){
            try{
                dataOut.writeInt(connectionID);
                dataOut.flush();

                while(true){
                    controller.route(dataIn.readUTF());
                }
            }catch (IOException e) {
                System.out.println("IOException from ServerSideConnection run()");
            }
        }
    }
}

