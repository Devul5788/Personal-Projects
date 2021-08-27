
/**
 * Write a description of PhraseFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PhraseFilter implements Filter{
    private String where;
    private String phrase;
    public PhraseFilter(String wh, String ph){
        where = wh;
        phrase = ph;
    }
    
    public boolean satisfies(QuakeEntry qe){
        if(where.equals("end") && qe.getInfo().endsWith(phrase)){
             return true;
        }
        if(where.equals("start") && qe.getInfo().startsWith(phrase)){
             return true;
        }
        if(where.equals("any") && qe.toString().indexOf(phrase) != -1){
            return true;
        }
        return false;
    }
    
    public String getName(){
        return "Phrase Filter";
    }
}
