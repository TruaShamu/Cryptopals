import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

// Cryptopals  challenge 1.3 :  Single-byte XOR cipher.
public class challenge4 {

    public static void main(String[] args) throws IOException, DecoderException {
        BufferedReader br = new BufferedReader(new FileReader("notepad.txt"));
        double maxScore = 0;
        String ans = " ";
        for (int i=0; i< 327; i++) {
            char[] inp = br.readLine().toCharArray();
            byte[] arr = Hex.decodeHex(inp);
            for (int j=0; j< 256; j++ ) {
                byte[] result = xorWithKey(arr, (byte) j);
                String s = new String(result, StandardCharsets.UTF_8);
                double curScore = getScore(s);
                if (curScore > maxScore) {
                    ans = s;
                    maxScore = curScore;
                }
            }


        }
        System.out.println(ans);
    }

    public static byte[] xorWithKey(byte[] arr, byte key) {
        byte[] output = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            output[i] = (byte) (key ^ arr[i]);
        }
        return output;
    }

    public static double getScore(String input) {
        HashMap<Character, Double> m = new HashMap();
        m.put('a', 0.0812);
        m.put('b', 0.0149);
        m.put('c', 0.0271);
        m.put('d', 0.0432);
        m.put('e', 0.1202);
        m.put('f', 0.0230);
        m.put('g', 0.0202);
        m.put('h', 0.0592);
        m.put('i', 0.0731);
        m.put('j', 0.001);
        m.put('k', 0.0069);
        m.put('l', 0.0398);
        m.put('m', 0.0261);
        m.put('n', 0.0695);
        m.put('o', 0.0768);
        m.put('p', 0.0182);
        m.put('q', 0.0011);
        m.put('r', 0.0602);
        m.put('s', 0.0628);
        m.put('t', 0.091);
        m.put('u', 0.0288);
        m.put('v', 0.0111);
        m.put('w', 0.0209);
        m.put('x', 0.0017);
        m.put('y', 0.0211);
        m.put('z', 0.0007);
        m.put(' ', 0.1);
        double score = 0;

        for (int i=0; i< input.length(); i++) {
            char c = input.charAt(i);
            if (m.containsKey(c)) {
                score += m.get(c);
            }
        }
        return score / (double)input.length();
    }
}
