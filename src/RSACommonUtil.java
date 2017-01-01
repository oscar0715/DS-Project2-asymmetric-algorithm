import java.math.BigInteger;

/**
 * Created by Jiaming on 10/6/16.
 */
public class RSACommonUtil {
    /**
     * Use the public key (N,e) to encrypt the message
     *
     * @param message
     * @param n
     * @param e
     * @return byte[]
     */
    public byte[] rsaEncrypt(String message, BigInteger n, BigInteger e) {
        BigInteger m = new BigInteger(message.getBytes()); // m is the original clear text
        BigInteger c = m.modPow(e, n);
        return c.toByteArray();
    }

    /**
     * Use the public key (N,e) to encrypt the message
     *
     * @param message
     * @param n
     * @param e
     * @return byte[]
     */
    public byte[] rsaEncrypt(BigInteger message, BigInteger n, BigInteger e) {
        BigInteger c = message.modPow(e, n);
        return c.toByteArray();
    }

    /**
     * Decrypt message
     * @param message
     * @param n
     * @param d
     * @return
     */
    public byte[] rsaDecypt(byte[] message, BigInteger n, BigInteger d ){
        BigInteger c = new BigInteger(message);
        BigInteger clear = c.modPow(d, n);
        return clear.toByteArray();
    }

}
