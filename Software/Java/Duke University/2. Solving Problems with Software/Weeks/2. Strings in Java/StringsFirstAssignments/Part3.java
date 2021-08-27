
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class Part3 {
    public String twoOccurrences (String stringa, String stringb, int firsto){
        if(firsto==-1){
            return "FALSE";
        }
        int stopIndex=stringb.indexOf(stringa,firsto+stringa.length());
        if(stopIndex>=2){
            return "TRUE";
        } 
        return "FALSE";
    }
    public void test(){
        String stringb = "aaaab";
        System.out.println("String B is: " + stringb);
        String stringa = "aa";
        int firsto = stringb.indexOf(stringa);
        System.out.println(twoOccurrences(stringa, stringb, firsto));
        System.out.println(lastPart(stringa, stringb));
        
        stringb = "banana";
        System.out.println("String B is: " + stringb);
        stringa = "na";
        firsto = stringb.indexOf(stringa);
        System.out.println(twoOccurrences(stringa, stringb, firsto));
        System.out.println(lastPart(stringa, stringb));
        
        stringb = "abbbcbc";
        System.out.println("String B is: " + stringb);
        stringa = "bc";
        firsto = stringb.indexOf(stringa);
        System.out.println(twoOccurrences(stringa, stringb, firsto));
        System.out.println(lastPart(stringa, stringb));
    }
    public String lastPart (String stringa, String stringb){
       String result = "";
       int firstOcurrence = stringb.indexOf(stringa);
       if(firstOcurrence == -1){
           return stringb;
       }
       else{
        int sizea = stringa.length();
        String test = stringb.substring(firstOcurrence + sizea);
        return test;
    }   
    }
}
