package pl.edu.agh.kis.pz1;

import java.io.*;
import java.net.*;

public class Client {
    private ClientConnection cc;
    private Game game;

    public Client(){
        this.connectToServer();
    }

    public void connectToServer(){
        cc = new ClientConnection();
        if(cc.isConnectionStable()){
            System.out.println("Connection to server established");

            this.game = new Game(cc, cc.getClientId());
        }
    }
}
