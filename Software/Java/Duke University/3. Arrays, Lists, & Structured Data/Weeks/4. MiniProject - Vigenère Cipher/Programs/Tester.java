
/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class Tester {
    public void testCaesarCipher(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        //String message = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        CaesarCipher cc = new CaesarCipher(15);
        String encrypted = cc.encrypt(message);
        System.out.println("The encrypted message was: \n" + encrypted);
        String decrypted = cc.decrypt(encrypted);
        System.out.println("The decrypted message using the key was: \n" + decrypted);
    }
    
    public void testCaesarCracker(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        //String message = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        CaesarCracker cc = new CaesarCracker('a');
        String decrypted = cc.decrypt(message);
        System.out.println("The decrypted message using the key was: \n" + decrypted);
    }
    
    public void testVigenereCipher(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        int[]key = {17, 14, 12, 4};
        //String message = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        VigenereCipher vc = new VigenereCipher(key);
        String encrypted = vc.encrypt(message);
        System.out.println("The encrypted message using the key was: \n" + encrypted);
        String decrypted = vc.decrypt(encrypted);
        System.out.println("The decrypted message using the key was: \n" + decrypted);
    }
    
    public void testVigenereBreaker(){
        VigenereBreaker vb = new VigenereBreaker();
        String decrypted = vb.breakVigenere();
        System.out.println("The decrypted message using the keys was: \n\n" + decrypted);
    }
    
    public void keysUsed(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        VigenereBreaker vb = new VigenereBreaker();
        int[]keys = vb.tryKeyLength(message, 4, 'e');
        System.out.println("The keys are: \n");
        for(int k = 0; k < keys.length; k++){
            System.out.println(keys[k]);
        }
    }
}
