
/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class CaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int mainkey;
    
    public CaesarCipher(int key){
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet = alphabet.substring(key)+ alphabet.substring(0,key);
        mainkey = key;
    }
    
    public String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder(input);
        for(int i = 0; i < encrypted.length(); i++) {
            shiftedAlphabet = shiftedAlphabet.toUpperCase();
            alphabet = alphabet.toUpperCase();
            char currChar = encrypted.charAt(i);
            int idx = alphabet.indexOf(currChar);
            if(idx == -1){
                alphabet = alphabet.toLowerCase();
                shiftedAlphabet = shiftedAlphabet.toLowerCase();
                currChar = encrypted.charAt(i);
                idx = alphabet.indexOf(currChar);
            }
            if(idx != -1){
                char newChar = shiftedAlphabet.charAt(idx);
                encrypted.setCharAt(i, newChar);
            }
        }
        return encrypted.toString();
    }
    
    public String decrypt(String input){
        CaesarCipher cc = new CaesarCipher(26 - mainkey);
        String decrypted = cc.encrypt(input);
        return decrypted;
    }
}
