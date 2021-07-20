import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

// Cryptopals  challenge 1.2 : Fixed XOR
public class challenge2 {

    public static void main(String[] args) throws IOException, DecoderException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] hex1 = br.readLine().toCharArray();
        char[] hex2 = br.readLine().toCharArray();
        int length = hex1.length;

        byte[] dec1 = Hex.decodeHex(hex1);
        byte[] dec2 = Hex.decodeHex(hex2);
        byte[] res = new byte[dec1.length];

        int i = 0;
        for (byte b : dec1) {
            res[i] = (byte) (b ^ dec2[i++]);
        }
        char[] base64 = Hex.encodeHex(res);
        for (char o : base64) {
            System.out.print(o);
        }
        System.out.println();
    }
}
