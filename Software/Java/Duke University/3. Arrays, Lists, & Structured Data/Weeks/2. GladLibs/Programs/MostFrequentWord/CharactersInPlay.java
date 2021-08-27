
/**
 * Write a description of CharactersInPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
public class CharactersInPlay{
    private ArrayList<String> charName;
    private ArrayList<Integer> count;
    
    public CharactersInPlay(){
        charName = new ArrayList<String>();
        count = new ArrayList<Integer>();
    }
    
    public void update(String person){
        String person1 = person.toLowerCase();
        int idx = charName.indexOf(person);
        if(idx == -1){
            charName.add(person);
            count.add(1);
        }
        else{
            int value = count.get(idx);
            count.set(idx, value+1);
        }
    }
    
    public void findAllCharacters(){
        charName.clear();
        count.clear();
        FileResource resource = new FileResource();
        char dot = '.';
        for(String line: resource.lines()){
            int firstDot = line.indexOf(dot);
            if(firstDot != -1){
                String name = line.substring(0, firstDot);
                update(name);
            }
        }
    }
    
    public void tester(){
        findAllCharacters();
        for(int i = 0; i < charName.size(); i++){
            if(count.get(i) > 1){
                System.out.println(charName.get(i) + " " + count.get(i));
            }
        }
        charactersWithNumParts(10, 15);
    }
    
    public void charactersWithNumParts(int num1, int num2){
        if(num1 <=  num2){
            System.out.println("The Characters who have lines between: " + num1 + " and " + num2 + " are: \n");
            for(int i = 0; i < charName.size(); i++){
                if(count.get(i) > 1){
                    if(count.get(i) >= num1 && count.get(i) <= num2){
                        System.out.println(charName.get(i) + " " + count.get(i));
                    }
                }
            }
        }
    }
}
