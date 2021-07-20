import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

// Cryptopals  challenge 1.1 : Convert hex to base64.
public class challenge1 {

    public static void main(String[] args) throws IOException, DecoderException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] hex = br.readLine().toCharArray();
        byte[] decodedHex = Hex.decodeHex(hex);
        String ret = Base64.encodeBase64String(decodedHex);
        System.out.println(ret);
    }
}
