
/**
 * Write a description of WordPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordPlay {
    public boolean isVowel(char ch){
        String vowels = "AEIOUaeiou";
        String answer = "";
        for(int i = 0; i < vowels.length(); i++){
            char currChar = vowels.charAt(i);
            int idx = vowels.indexOf(currChar);
            if (currChar == ch){
                answer = "true";
            }
        }
        if (answer == "true"){
           return true; 
        }
        return false;
    }
    
    public void testisVowel(){
        Boolean check = isVowel('c');
        if (check == true){
            System.out.println("true");
        }
        else {
            System.out.println("false");
        }
    }
    
    public String replaceVowels(String phrase, char ch){
        StringBuilder changed = new StringBuilder(phrase);
        String vowels = "AEIOUaeiou";
        for (int i = 0; i < changed.length(); i++){
             Boolean check = isVowel(changed.charAt(i));
             char currChar = changed.charAt(i);
             if (check == true){
                 changed.setCharAt(i, ch);
             }
        }
        return changed.toString();
    }
    
    public void testreplaceVowels(){
        System.out.println(replaceVowels("AEImMOUaeiou", '*'));
    }
    
    public String emphasize(String phrase, char ch){
        StringBuilder emphasized = new StringBuilder(phrase);
        for (int i = 0; i < emphasized.length(); i++){
             char currChar = emphasized.charAt(i);
             if (currChar == ch){
                 int positionNumber = (i+1)%2;
                 if (positionNumber == 0){
                     emphasized.setCharAt(i, '+');
                 }
                 if (positionNumber != 0){
                     emphasized.setCharAt(i,'*');
                 }
             }
        }
        return emphasized.toString();
    }
    
    public void testEmphasize(){
        System.out.println(emphasize("Mary Bella Abracadabra", 'a'));
    }
}