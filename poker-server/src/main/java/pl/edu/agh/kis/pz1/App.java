package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args){
        if( args.length == 0 )
        {
            System.exit( 0 );
        }
        int maxPlayers = Integer.parseInt( args[ 0 ] );
        SocketServer server = new SocketServer(3000, maxPlayers);
        server.acceptConnections();
    }
}
