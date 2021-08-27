
/**
 * Write a description of WordFrequencies here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
public class WordFrequencies {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public WordFrequencies(){
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    public void findUnique(){
        myWords.clear();
        myFreqs.clear();
        FileResource resource = new FileResource();
        for(String word: resource.words()){
            word = word.toLowerCase();
            int idx = myWords.indexOf(word);
            if(idx == -1){
                myWords.add(word);
                myFreqs.add(1);
            }
            else{
                int value = myFreqs.get(idx);
                myFreqs.set(idx, value+1);
            }
        }
    }
    
    public void tester(){
        findUnique();
        System.out.println("The number of unique words were: " + myWords.size());
        for(int k = 0; k < myWords.size(); k++){
            System.out.println(myWords.get(k) + "\t" + myFreqs.get(k));
        }
        int highestFreqWord = findIndexOfMax();
        System.out.println("The word that occurs most often and its count are: "
                            + myWords.get(highestFreqWord) + " " + myFreqs.get(highestFreqWord));
    }
    
    public int findIndexOfMax(){
        int maxIdx = 0;
        for (int i = 0; i < myWords.size(); i++){
            if(myFreqs.get(i) > myFreqs.get(maxIdx)){
                maxIdx = i;
            }
        }
        return maxIdx;
    }
}
