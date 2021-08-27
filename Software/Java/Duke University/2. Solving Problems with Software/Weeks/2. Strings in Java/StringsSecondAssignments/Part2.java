
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    public int howMany(String stringa, String stringb){
        int currIndex = 0; 
        int count = 0;
        currIndex = stringa.indexOf(stringb);
        while (currIndex != -1){
            int lastIndex = currIndex + stringb.length();
            currIndex = stringa.indexOf(stringb, lastIndex);
            count = count + 1;
        }
        return count;
    }
    
    public void testHowMany (){
       String stringa = "abcabc"; 
       System.out.println("The line is: " + stringa);
       String stringb = "a";
       System.out.println("The the thing you want to search is: " + stringb);
       int count = howMany(stringa, stringb);
       System.out.println("Count: " + count);
       
       stringa = "asbvhufbsavbbvpidbspvipbgquhiphipufnpiuashipbagyebrv[aeiuv[ru"; 
       System.out.println("The line is: " + stringa);
       stringb = "b";
       System.out.println("The the thing you want to search is: " + stringb);
       count = howMany(stringa, stringb);
       System.out.println("Count: " + count);
       
       stringa = "asvbubepuvbasbvypab[iubvunva[b[uba[iuvbi[abviub[ab[ub[vaiub[bra"; 
       System.out.println("The line is: " + stringa);
       stringb = "c";
       System.out.println("The the thing you want to search is: " + stringb);
       count = howMany(stringa, stringb);
       System.out.println("Count: " + count);
    }
}
