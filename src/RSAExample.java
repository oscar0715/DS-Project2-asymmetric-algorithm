/* Demonstrate RSA in Java using BigIntegers */

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

/**
 * RSA Algorithm from CLR
 * <p>
 * 1. Select at random two large prime numbers p and q. 2. Compute n by the
 * equation n = p * q. 3. Compute phi(n)= (p - 1) * ( q - 1) 4. Select a small
 * odd integer e that is relatively prime to phi(n). 5. Compute d as the
 * multiplicative inverse of e modulo phi(n). A theorem in number theory asserts
 * that d exists and is uniquely defined. 6. Publish the pair P = (e,n) as the
 * RSA public key. 7. Keep secret the pair S = (d,n) as the RSA secret key. 8.
 * To encrypt a message M compute C = M^e (mod n) 9. To decrypt a message C
 * compute M = C^d (mod n)
 */
public class RSAExample {

    public static void main(String[] args) {
        // Each public and private key consists of an exponent and a modulus
        BigInteger n; // n is the modulus for both the private and public keys
        BigInteger e; // e is the exponent of the public key
        BigInteger d; // d is the exponent of the private key

        Random rnd = new Random();

        // Step 1: Generate two large random primes.
        // We use 400 bits here, but best practice for security is 2048 bits.
        // Change 400 to 2048, recompile, and run the program again and you will
        // notice it takes much longer to do the math with that many bits.
        BigInteger p = new BigInteger(400, 100, rnd);
        BigInteger q = new BigInteger(400, 100, rnd);
        //        BigInteger p = new BigInteger("3462634601");
        //        BigInteger q = new BigInteger("3462634601");

        // Step 2: Compute n by the equation n = p * q.
        n = p.multiply(q);
        n = new BigInteger("2688520255179015026237478731436571621031218154515572968727588377065598663770912513333018006654248650656250913110874836607777966867106290192618336660849980956399732967369976281500270286450313199586861977623503348237855579434471251977653662553");

        // Step 3: Compute phi(n) = (p-1) * (q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(
                BigInteger.ONE));

        // Step 4: Select a small odd integer e that is relatively prime to phi(n).
        // By convention the prime 65537 is used as the public exponent.
        e = new BigInteger("65537");

        // Step 5: Compute d as the multiplicative inverse of e modulo phi(n).
        d = e.modInverse(phi);

        d = new BigInteger("339177647280468990599683753475404338964037287357290649639740920420195763493261892674937712727426153831055473238029100340967145378283022484846784794546119352371446685199413453480215164979267671668216248690393620864946715883011485526549108913");

        System.out.println("e = " + e);  // Step 6: (e,n) is the RSA public key
        System.out.println("d = " + d);  // Step 7: (d,n) is the RSA private key
        System.out.println("n = " + n);  // Modulus for both keys
        System.out.println();

        // Encode a simple message. For example the letter 'A' in UTF-8 is 65
        byte[] array = {0, -87, -71, 62, 43, 105, -13, 53, 123, 102, 35, 71, -29, 86, -79, -42, 40, -19, -74, -11, 0};
        BigInteger m = new BigInteger(array);

        // Step 8: To encrypt a message M compute C = M^e (mod n)
//        BigInteger c = m.modPow(e, n);
        BigInteger c = m.modPow(d, n);
       

        // Step 9: To decrypt a message C compute M = C^d (mod n)
//        BigInteger clear = c.modPow(d, n);
        BigInteger clear = c.modPow(e, n);
        System.out.println("Cypher text = " + c);
        System.out.println("Clear text = "
                + Arrays.toString(clear.toByteArray())); // Should be "65"

        // Step 8 (reprise) Encrypt the string 'Hello'
        //        String s = "RSA is way cool.";
        //        m = new BigInteger(s.getBytes()); // m is the original clear text
        //        c = m.modPow(e, n);     // Do the encryption, c is the cypher text
        // Step 9 (reprise) Decrypt...
        //        clear = c.modPow(d, n); // Decrypt, clear is the resulting clear text
        //        String clearStr = new String(clear.toByteArray());  // Decode to a string
        //        System.out.println("Cypher text = " + c);
        //        System.out.println("Clear text = " + clearStr);
    }
}
