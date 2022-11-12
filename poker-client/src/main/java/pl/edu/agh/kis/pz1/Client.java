package pl.edu.agh.kis.pz1;

public class Client {
    public Client(){
        this.connectToServer();
    }

    public void connectToServer(){
        ClientConnection cc = new ClientConnection();
        if(cc.isConnectionStable()){
            System.out.println("Connection to server established");

            new Game(cc);
        }
    }
}
