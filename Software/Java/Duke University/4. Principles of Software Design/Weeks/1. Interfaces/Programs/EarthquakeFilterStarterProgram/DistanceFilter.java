
/**
 * Write a description of PhraseFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DistanceFilter implements Filter{
    private double maxDis;
    private Location loc;
    public DistanceFilter(double max, Location loca){
        loc = loca;
        maxDis = max;
    }
    
    public boolean satisfies(QuakeEntry qe){
        return qe.getLocation().distanceTo(loc)/1000 < maxDis;
    }
    
    public String getName(){
        return "Distance Filter";
    }
}
