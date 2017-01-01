import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Jiaming on 10/6/16.
 */
public class RSAKeyGenerator {
    private BigInteger p;
    private BigInteger q;

    // n is the modulus for both the private and public keys
    private BigInteger n;

    // e is the exponent of the public key
    private BigInteger e;

    // d is the exponent of the private key
    private BigInteger d;

    public RSAKeyGenerator() {

        // Step 1: Generate two large random primes.
        Random rnd = new Random();
        this.p = new BigInteger(400, 100, rnd);
        this.q = new BigInteger(400, 100, rnd);

        // Step 2: Compute n by the equation n = p * q.
        this.n = p.multiply(q);

        // Step 3: Compute phi(n) = (p-1) * (q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(
                BigInteger.ONE));

        // Step 4: Select a small odd integer e that is relatively prime to phi(n).
        // By convention the prime 65537 is used as the public exponent.
        this.e = new BigInteger("65537");

        // Step 5: Compute d as the multiplicative inverse of e modulo phi(n).
        d = e.modInverse(phi);
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }

    public static void main(String[] args) {
        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator();
        System.out.println("[N] = " + rsaKeyGenerator.getN());
        System.out.println("[E] = " + rsaKeyGenerator.getE());
        System.out.println("[D] = " + rsaKeyGenerator.getD());
    }
}
