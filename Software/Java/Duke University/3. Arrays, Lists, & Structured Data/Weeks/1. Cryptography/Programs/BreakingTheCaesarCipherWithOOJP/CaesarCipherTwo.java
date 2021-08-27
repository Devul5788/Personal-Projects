
/**
 * Write a description of CaesarCipherTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarCipherTwo{
    private String alphabet;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    private int mainkey1;
    private int mainkey2;
    
    public CaesarCipherTwo(int key1, int key2){
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet1 = alphabet.substring(key1)+ alphabet.substring(0,key1);
        shiftedAlphabet2 = alphabet.substring(key2)+ alphabet.substring(0,key2);
        mainkey1 = key1;
        mainkey2 = key2;
    }
    
    public String encryptTwoKeys(String input){
        int positionNumber = 0;
        char currChar = '0';
        int idx = 0;
        StringBuilder doubleEncrypted = new StringBuilder(input);
        for(int i = 0; i < doubleEncrypted.length(); i++){
            //shiftedAlphabet1 != null && shiftedAlphabet2 != null && alphabet != null){
            shiftedAlphabet1 = shiftedAlphabet1.toUpperCase();
            shiftedAlphabet2 = shiftedAlphabet2.toUpperCase();
            alphabet = alphabet.toUpperCase();
            positionNumber = (i)%2;
            currChar = doubleEncrypted.charAt(i);
            idx = alphabet.indexOf(currChar);
            if (idx == -1){
                alphabet = alphabet.toLowerCase();
                shiftedAlphabet1 = shiftedAlphabet1.toLowerCase();
                shiftedAlphabet2 = shiftedAlphabet2.toLowerCase();
                currChar = doubleEncrypted.charAt(i);
                idx = alphabet.indexOf(currChar);
            }
            if (positionNumber == 0){
                if (Character.isLetter(currChar)){
                    char newChar1 = shiftedAlphabet1.charAt(idx);
                    doubleEncrypted.setCharAt(i, newChar1);
                }
               }   
            else{
                if (Character.isLetter(currChar)){
                    char newChar2 = shiftedAlphabet2.charAt(idx); 
                    doubleEncrypted.setCharAt(i, newChar2);
                }
            }
        }
        return doubleEncrypted.toString();
    }
    
    public String decryptTwoKeys(String input){
        CaesarCipherTwo cc = new CaesarCipherTwo(26-mainkey1, 26-mainkey2);
        String decrypted = cc.encryptTwoKeys(input);
        return decrypted;
    }
}
