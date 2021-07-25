import org.apache.commons.codec.binary.Hex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

//why am i doing this to myself when i could be playing genshin >:(
public class challenge6 {
    public static void main(String[] args) throws IOException {
        String str1 = "this is a test";
        String str2 = "wokka wokka!!!";

        byte[] byte1 = str1.getBytes();
        byte[] byte2 = str2.getBytes();
        System.out.println(Arrays.toString(byte1));
        System.out.println(Arrays.toString(byte2));
        System.out.println(hamming(byte1, byte2));

        //Read input, convert to bytes.
        BufferedReader br = new BufferedReader(new FileReader("notepad.txt"));
        String fileContent = "";
        String str = "";
        while ((str = br.readLine()) != null) {
            fileContent += str;
        }
        byte[] inp = Base64.getDecoder().decode(fileContent.getBytes("UTF-8"));

        ArrayList<pair> norm = new ArrayList<>();

        //Normalized.
        for (int keysize = 2; keysize < 40; keysize++) {
            byte[] block1 = Arrays.copyOfRange(inp, 0 * keysize, 1 * keysize);
            byte[] block2 = Arrays.copyOfRange(inp, 1 * keysize, 2 * keysize);
            byte[] block3 = Arrays.copyOfRange(inp, 2 * keysize, 3 * keysize);
            byte[] block4 = Arrays.copyOfRange(inp, 3 * keysize, 4 * keysize);
            int total = hamming(block1, block2) + hamming(block1, block3) + hamming(block1, block4)
                    + hamming(block2, block3) + hamming(block2, block4) + hamming(block3, block4);
            norm.add(new pair(keysize, (total / keysize)));
        }

        Collections.sort(norm);


            int keySize = norm.get(0).key;
            byte[] key = new byte[keySize];

            //Break up the input into keysize length blocks.
            byte[][] blocks = new byte[inp.length / keySize][keySize];
            int idx = 0;
            int numBlocks = inp.length / keySize;
            for (int j = 0; j < numBlocks; j++) {
                for (int l = 0; l < keySize; l++) {
                    blocks[j][l] = inp[idx++];
                }
            }

            //Find the transpose.
            byte[][] transpose = new byte[keySize][inp.length / keySize];
            for (int r = 0; r < blocks.length; r++) {
                for (int c = 0; c < blocks[0].length; c++) {
                    transpose[c][r] = blocks[r][c];
                }
            }


            for (int k = 0; k < keySize; k++) {
                double maxScore = 0;

                for (int j = 0; j < 255; j++) {
                    byte[] decoded = xorWithKey(transpose[k], (byte) j);
                    double score = getScore(new String(decoded, StandardCharsets.UTF_8));

                    if (score > maxScore) {
                        maxScore = score;
                        key[k] = (byte) j;
                    }
                }
                // System.out.println();
            }

        System.out.println("Guessed Key: " + new String(key, StandardCharsets.UTF_8));
        System.out.println(new String(rollingXor(inp, key), StandardCharsets.UTF_8));
    }


    /*
     * What we did, was loop through each element in the byte array.
     *  Then for each byte, loop through every bit, and count the number of differences.
     */
    public static int hamming(byte[] s1, byte[] s2) {
        int cnt = 0;
        for (int i = 0; i < s1.length; i++) {
            //Loop through each bit in the byte.
            for (int j = 0; j < 8; j++) {
                int s1bit = (s1[i] & (1 << j));
                int s2bit = (s2[i] & (1 << j));
                if (s1bit != s2bit) {
                    cnt++;

                }
            }
        }
        return cnt;
    }

    public static byte[] xorWithKey(byte[] arr, byte key) {
        byte[] output = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            output[i] = (byte) (key ^ arr[i]);
        }
        return output;
    }

    public static byte[] rollingXor(byte[] message, byte[] key) {
        byte[] output = new byte[message.length];
        for (int i = 0; i < message.length; i++) {
            output[i] = (byte) (message[i] ^ key[i % (key.length)]);
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

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (m.containsKey(c)) {
                score += m.get(c);
            }
        }
        return score / (double) input.length();
    }


    public static class pair implements Comparable<pair> {
        public int key, value;

        public pair(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public int compareTo(pair other) {
            return (this.value - other.value);
        }
    }
}

