
/**
 * Write a description of Part4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class Part4 {
    public void findURL (){
        URLResource website = new URLResource ("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
        System.out.println("Here are the Youtube URLs:");
        for (String word: website.words()){
            word.toLowerCase();
            String youtube = "youtube.com";
            String qmarks =  ("\"");
            int firstOccurence = word.indexOf(youtube);
            if (firstOccurence != -1){
            int lastIndex = word.indexOf(qmarks, firstOccurence);
            int firstIndex = word.lastIndexOf(qmarks, firstOccurence);
            String youtubeURL = word.substring(firstIndex, lastIndex+1);
            System.out.println(youtubeURL);
        }
    }
    }
}