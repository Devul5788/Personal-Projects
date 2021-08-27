
/**
 * Write a description of TestCaesarCipherTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class TestCaesarCipherTwo {
    public String halfOfString(String message, int start){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < message.length(); i++){
            if( (i-start) % 2 == 0){
               char ch = message.charAt(i);
               output.append(ch);
            }
        }
        return output.toString();
    } 
    
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
        FileResource fr = new FileResource();
        String message = fr.asString();
        //String message = "Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!";
        //CaesarCipherTwo cc = new CaesarCipherTwo(26-14, 26);
        //String encrypted = cc.encryptTwoKeys(message);
        //System.out.println("The encrypted message was: \n" + encrypted);
        //String decrypted = cc.decryptTwoKeys(encrypted);
        //System.out.println("The decrypted message using the key was: \n" + decrypted);
        String decrypted2 = breakCaesarCipher(message);
        System.out.println("The decrypted message without knowing the key was: " + decrypted2);
    }
    
    public int getKey(String s){
        int [] alphaCounted = countLetters(s);
        int maxDex = maxIndex(alphaCounted);
        int dkey = maxDex - 4;
        if (maxDex < 4){
            dkey = 26 - (4-maxDex);
        }
        return dkey;
    }
    
    public String breakCaesarCipher(String input){
        String firstChar = halfOfString(input, 0);
        String secondChar = halfOfString(input, 1);
        int firstCharKey = getKey(firstChar);
        int secondCharKey = getKey(secondChar);
        System.out.println("The Key for the encryption is: " + firstCharKey 
                            + " for even indexes and " + secondCharKey 
                            + " for odd indexes\n");
        CaesarCipherTwo cc = new CaesarCipherTwo(26-firstCharKey, 26-secondCharKey);
        return cc.encryptTwoKeys(input);
    }
}
