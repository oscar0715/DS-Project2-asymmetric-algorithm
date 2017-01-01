import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class TCPSpyUsingTEAandPasswordsAndRSA {

    public static void main(String args[]) {

        // common utilityies
        CommonUtil common = new CommonUtil();
        RSACommonUtil rsaCommonUtil = new RSACommonUtil();

        // Public Key
        BigInteger n = new BigInteger("2792758110555123651398582352589544937253514681668215161289178242469776616868623614619857991362258107994296325214675865309729683265089274094658600607306687415998013728419278384157519279950983972495130009514473992065778498507595349580096609491");
        BigInteger e = new BigInteger("65537");

        // arguments supply hostname
        Socket clientSocket = null;
        try {
            int serverPort = 7777;
            clientSocket = new Socket(args[0], serverPort);

            // bytes array to store message
            byte[] bytes = new byte[128];

            // the length of message
            int length = 0;

            // Used to read from server
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());

            // Used to send byte[] to server
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            // Used to read from System.in
            Scanner scanner = new Scanner(System.in);

            // generate the session key
            Random rnd = new Random();
            BigInteger key = new BigInteger(16 * 8, rnd);
            out.write(rsaCommonUtil.rsaEncrypt(key, n, e));

            // New a TEA object
            TEA tea = new TEA(key.toByteArray());

            // Input ID
            System.out.println("Enter your ID");
            String id = scanner.nextLine();
            out.write(tea.encrypt(id.getBytes()));

            // Input password
            System.out.println("Enter your Password");
            String password = scanner.nextLine();
            out.write(tea.encrypt(password.getBytes()));

            // Input location
            System.out.println("Enter your location");
            String location = scanner.nextLine();
            out.write(tea.encrypt(location.getBytes()));

            // get the message
            length = in.read(bytes);
            String responseMessage = common.getString(bytes, length);

            if (responseMessage.equals("IDPassed")) {
                System.out.println(
                        "Thank you. Your location was securely transmitted to Intelligence Headquarters.");
            } else {
                System.out.println("Not a valid user-id or password.");
            }

        } catch (IOException exception) {
            System.out.println("IO Exception:" + exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception:" + exception.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException exception) {
                // ignore exception on close
            }
        }
    }
}