
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class Part2 {
    public void cgRatio (String dna){
        int cCount = 0;
        int gCount = 0;
        int cIndex = dna.indexOf("c");
        int gIndex = dna.indexOf("g");
        while (true){
             if (cIndex == -1 || gIndex == -1 || cIndex > dna.length()-1 || gIndex > dna.length()-1){
                 break;
             }
             cCount = cCount+1;
             gCount = gCount+1;;
             cIndex = dna.indexOf("c", cIndex + 1);
             gIndex = dna.indexOf("g", gIndex + 1);
        }
        int cgCount = cCount+gCount;
        float stringLength = dna.length();
        float answer = cgCount/stringLength;
        System.out.println("This is the ratio of C:G in dna: " + answer);
    }
    
    public void testcgRatio (){
        //cgRatio("acghcdgcagbcggcvdabcouvasdgyapdubcgcgacgagcsgdascgacgcgcyugcdgagcgcagcgcggcgcgcgcaggcgcgcga");
        countCTG("CTGGGGGGCTGGJHLGLCTGJHCTGKJHHCTGCTGCTGCTGCTCGCTHPDJCCTG");
    }
    
    public void countCTG (String dna){
        int CTGcount = 0;
        int CTGIndex = dna.indexOf("CTG");
        while (true){
            if (CTGIndex == -1 || CTGIndex > dna.length()-3){
                break;
            }
            CTGIndex = dna.indexOf("CTG", CTGIndex + 1);
            CTGcount = CTGcount+1;
        }
        System.out.println("Count: " + CTGcount);
    }
}
