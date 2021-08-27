import edu.duke.*;
public class WordLengths {
    public int[] countWordLengths(FileResource resource, int [] counts){
        char letter = '0';
        int numberOfLetters = 0;
        int numberOfWords = 0;
        String[] words = new String[31];
        int maxlength = 0;
        for(String s: resource.words()){
            numberOfLetters = 0;
            for(int i = 0; i < s.length(); i++){
                letter = s.charAt(i);
                if(Character.isLetter(letter)){
                    numberOfLetters += 1;
                }
                if(letter == ('\'')|| letter == ('-')){
                    numberOfLetters += 1;
                }
            }
            if (numberOfLetters > 30){
                numberOfLetters = 30;
            }
            if (numberOfLetters >= maxlength){
                maxlength = numberOfLetters;
            }
            counts[numberOfLetters] += 1;
            if(words[numberOfLetters] == null){
                words[numberOfLetters] = "";
            }
            words[numberOfLetters] += s + " "; 
        }
        for(int k = 0; k <= maxlength; k++){
            if(counts[k] != 0){
               System.out.println(counts[k] + " words of length " + k + ": " + words[k]);
            }
        }
        return counts;
    }
    
    public void testCountWordLengths(){
        FileResource fr = new FileResource();
        int[] counts = new int[31];
        int[] countsArray = countWordLengths(fr, counts);
        indexOfMax(countsArray);
    }
    
    public int indexOfMax(int [] values){
        int maxIndex = 0;
        for(int k = 0; k <= 30; k++){
            if(values[k] != 0){
                if (values[k] >= maxIndex){
                    maxIndex = values[k];
                }
            }
        }
        System.out.println(maxIndex);
        return maxIndex;
    }
}
