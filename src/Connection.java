import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

class Connection extends Thread {
    // manage read from client and write to client
    private DataInputStream in;
    private DataOutputStream out;

    private Socket clientSocket;

    // count of visitors
    private int count;

    // common utils
    private RSACommonUtil rsaCommonUtil;
    private CommonUtil commonUtil;
    private KMLWriter kmlWriter;

    // map to store id and password of clients
    private Map<String, String> spyMap;

    private PasswordHash hash;
    private String salt;
    private BigInteger n;
    private BigInteger d;

    public Connection(Socket aClientSocket,
                             int count,
                             Map<String, String> map,
                             BigInteger n,
                             BigInteger d,
                             KMLWriter writer) {
        try {
            this.clientSocket = aClientSocket;
            this.in = new DataInputStream(clientSocket.getInputStream());
            this.out = new DataOutputStream(clientSocket.getOutputStream());
            this.hash = new PasswordHash();
            this.salt = "asdgaskjbg5234ewgrhmlf213klnonfeoiwa";
            this.spyMap = map;
            this.rsaCommonUtil = new RSACommonUtil();
            this.commonUtil = new CommonUtil();
            this.kmlWriter = writer;
            this.count = count;
            this.d = d;
            this.n = n;

            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            // byte array to store received messgae;
            byte[] bytes = new byte[128];

            // to store the length of received message;
            int length = 0;

            // get seesionkey
            length = in.read(bytes);
            byte[] key = rsaCommonUtil.rsaDecypt(Arrays.copyOf(bytes, length),
                    n,
                    d);

            // new a TEA object
            TEA tea = new TEA(key);

            // get id
            length = in.read(bytes);
            String id = commonUtil.getStringAfterDecrypt(tea, bytes, length);

            // get password
            length = in.read(bytes);
            String password = commonUtil.getStringAfterDecrypt(tea,
                    bytes,
                    length);
            password = hash.getHashValue(salt + password);

            // get location
            length = in.read(bytes);
            String location = commonUtil.getStringAfterDecrypt(tea,
                    bytes,
                    length);

            // validate id and password
            if (!(spyMap.containsKey(id) && spyMap.get(id).equals(password))) {
                out.write("IDNotPass".getBytes());
                System.out.print("Got visit " + count + " from " + id);
                System.out.println(
                        ". Illegal Password attempt. This may be an attack.");
                return;
            }

            // location management
            // the path of the kml file is "./src/SecretAgents.kml"
            out.write("IDPassed".getBytes());
            kmlWriter.change(id, location);
            kmlWriter.writeToFile();
            System.out.println("Got visit " + count + " from " + id);

        } catch (EOFException e) {
            e.printStackTrace();
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("readline:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/}
        }
    }



}
