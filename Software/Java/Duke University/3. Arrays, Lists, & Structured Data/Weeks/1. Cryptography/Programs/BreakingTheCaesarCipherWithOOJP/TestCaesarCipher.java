
/**
 * Write a description of TestCaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class TestCaesarCipher {
    public int[] countLetters(String message){
        String alph = "abcdefghijklmnopqestuvwxyz";
        int[] counts = new int[26];
        for(int i=0; i < message.length(); i++){
            char ch = Character.toLowerCase(message.charAt(i));
            int idx = alph.indexOf(ch);
            if (idx != -1){
                counts[idx] += 1;
            }
        }
        return counts;
    }
    
    public int maxIndex(int[] vals){
        int maxIdx = 0;
        for (int i = 0; i < vals.length; i++){
            if(vals[i] > vals[maxIdx]){
                maxIdx = i;
            }
        }
        return maxIdx;
    }
    
    public void simpleTests(){
        //FileResource fr = new FileResource();
        //String message = fr.asString();
        String message = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        CaesarCipher cc = new CaesarCipher(15);
        String encrypted = cc.encrypt(message);
        System.out.println("The encrypted message was: \n" + encrypted);
        String decrypted = cc.decrypt(encrypted);
        System.out.println("The decrypted message using the key was: \n" + decrypted);
        String decrypted2 = breakCaesarCipher(encrypted);
        System.out.println("The decrypted message without knowing the key was: " + decrypted2);
    }
    
    public String breakCaesarCipher(String input){
        int [] freqs = countLetters(input);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex - 4;
        if (maxDex < 4){
            dkey = 26 - (4-maxDex);
        }
        System.out.println("The key of the encrypted code was: " + dkey);
        CaesarCipher cc = new CaesarCipher(26-dkey);
        return cc.encrypt(input);
    }
}
