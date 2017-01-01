import java.util.Arrays;


public class CommonUtil {

    /**
     * Convert a byte array to string
     * Take first length bytes.
     * @param bytes
     * @param length
     * @return
     */
    public String getString(byte[] bytes, int length) {
        byte[] keyByte = Arrays.copyOf(bytes,length);
        String string = new String(keyByte);
        return string;
    }

    /**
     *  Use TEA to decrpt a bytes array
     *  Take first length bytes.
     * @param tea
     * @param bytes
     * @param length
     * @return String after TEA Decryption
     */
    public String getStringAfterDecrypt(TEA tea, byte[] bytes, int length) {
        byte[] byteArray = Arrays.copyOf(bytes, length);
        byte[] resultArray = tea.decrypt(byteArray);
        String string = new String(resultArray);
        return string;
    }


    public static void main(String[] args) {
    }

}