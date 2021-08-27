
/**
 * Write a description of DepthFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DepthFilter implements Filter{
    private double maxDep;
    private double minDep;
    public DepthFilter(double min, double max){
        maxDep = max;
        minDep = min;
    }
    
    public boolean satisfies(QuakeEntry qe){
        return qe.getDepth() >= minDep && qe.getDepth() <= maxDep;
    }
    
    public String getName(){
        return "Depth Filter";
    }
}
