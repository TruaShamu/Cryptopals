import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.DecoderException;



import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


// Cryptopals  challenge 1.3 :  Single-byte XOR cipher.
public class challenge5 {

    public static void main(String[] args) throws IOException, DecoderException {
        byte[] message = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal".getBytes(StandardCharsets.UTF_8);
        byte[] key = "ICE".getBytes(StandardCharsets.UTF_8);
        double maxScore = 0;
        String ans = " ";
        byte[] result = rollingXor(message, key);
        String hex = display(result);
        System.out.println(hex);

    }

    public static byte[] rollingXor(byte[] message, byte[] key) {
       byte[] output = new byte[message.length];
       for (int i = 0; i < message.length; i++ ) {
           output[i] = (byte) (message[i] ^ key[i % (key.length)]);
       }
       return output;

    }

    public static String display(byte[] b1) {
        StringBuilder strBuilder = new StringBuilder();
        for(byte val : b1) {
            strBuilder.append(String.format("%02x", val&0xff));
        }
        return strBuilder.toString();
    }


}
