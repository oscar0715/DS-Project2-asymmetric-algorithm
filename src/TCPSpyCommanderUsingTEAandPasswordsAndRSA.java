import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TCPSpyCommanderUsingTEAandPasswordsAndRSA {

    public static void main(String args[]) {

        // Map store clients' id and password
        Map<String, String> spyMap = new HashMap<>();
        KMLWriter writer = new KMLWriter();
        // initiate kml file
        writer.writeToFile();

        // counts of visitors
        int count = 0;

        // public key of RSA
        BigInteger n = new BigInteger("2792758110555123651398582352589544937253514681668215161289178242469776616868623614619857991362258107994296325214675865309729683265089274094658600607306687415998013728419278384157519279950983972495130009514473992065778498507595349580096609491");
        BigInteger d = new BigInteger("1018930362718518724210621521167716083962781170230079080848765444797516344750990421428741389313867794074364396176329624720993917060814303549922123710209896834676048511614959203564295219687316714266670140941956201047165088663914761422316836473");

        // initialize (temporary solution)
        spyMap.put("jamesb", "7FCDDD6191C985E41BFBF9C374D20A6D290FDF3E");
        spyMap.put("joem", "EA12B7C9145F5B80694ED353DB54ED2E046CAFC0");
        spyMap.put("mikem", "C9FB373B1AEA13EF6BDD58A77E39EE9432D8F3E3");
        spyMap.put("sean", "2384453C388CF0231302C23B9C7103865B6707F3");

        Socket clientSocket = null;
        try {

            System.out.println("Waiting for spies to visit...");

            // the server port we are using
            int serverPort = 7777;

            // Create a new server socket
            ServerSocket listenSocket = new ServerSocket(serverPort);


            /*
             * Block waiting for a new connection request from a client.
             * When the request is received, "accept" it, and the rest
             * the tcp protocol handshake will then take place, making 
             * the socket ready for reading and writing.
             */
            while (true) {
                clientSocket = listenSocket.accept();
                // If we get here, then we are now connected to a client.
                Connection c = new Connection(clientSocket,
                                                     ++count,
                                                     spyMap,
                                                     n,
                                                     d,
                                                     writer);
            }

            // Handle exceptions
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());

            // If quitting (typically by you sending quit signal) clean up sockets
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // ignore exception on close
            }
        }
    }
}
