package pl.edu.agh.kis.pz1;

public class App 
{
    public static void main(String[] args){
        if( args.length == 0 )
        {
            System.exit( 0 );
        }
        int maxPlayers = Integer.parseInt( args[ 0 ] );
        SocketServer server = new SocketServer(maxPlayers);
        server.acceptConnections();
    }
}
