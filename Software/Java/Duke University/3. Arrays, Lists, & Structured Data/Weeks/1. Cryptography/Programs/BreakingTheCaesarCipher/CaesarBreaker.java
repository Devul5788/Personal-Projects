
/**
 * Write a description of CaesarBreaker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class CaesarBreaker {
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
    
    public String decrypt(String encrypted){
        CaesarCipher cc = new CaesarCipher();
        int [] freqs = countLetters(encrypted);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex - 4;
        if (maxDex < 4){
            dkey = 26 - (4-maxDex);
        }
        System.out.println(dkey);
        return cc.encrypt(encrypted, 26-dkey);
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
    
    public void testDecrypt() {
        int key = 17;
        CaesarCipher cc = new CaesarCipher();
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encrypted = cc.encrypt(message, key);
        System.out.println(encrypted);
        String decrypted = decrypt(encrypted);
        System.out.println(decrypted);
    }
    
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
 
    public void testhalfOfString(){
        System.out.println(halfOfString("Qbkm Zgis", 1));
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
    
    public String decryptTwoKeys(String encrypted){
        CaesarCipher cc = new CaesarCipher();
        String firstChar = halfOfString(encrypted, 0);
        System.out.println(firstChar);
        String secondChar = halfOfString(encrypted, 1);
        System.out.println(secondChar);
        int firstCharKey = getKey(firstChar);
        int secondCharKey = getKey(secondChar);
        System.out.println("The Key for the encryption is: " + firstCharKey 
                            + " for even indexes and " + secondCharKey 
                            + " for odd indexes\nThe Encrypted code is: " + encrypted);
        return cc.encyptTwoKeys(encrypted, 26-firstCharKey, 26-secondCharKey);
    }
    
    public void testDecryptTwoKeys(){
        CaesarCipher cc = new CaesarCipher();
        int key1 = 2;
        int key2 = 20;
        FileResource fr = new FileResource();
        String message = fr.asString();
        //String message = "Akag tjw Xibhr awoa aoee xakex znxag xwko";
        //String encrypted = cc.encyptTwoKeys(message, key1, key2);
        //System.out.println(encrypted);
        String decrypted = decryptTwoKeys(message);
        System.out.println("The Decrypted code is: " + decrypted);
    }
}
