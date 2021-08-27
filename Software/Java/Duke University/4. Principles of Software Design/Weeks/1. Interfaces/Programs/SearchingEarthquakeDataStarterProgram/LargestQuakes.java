
/**
 * Write a description of LargestQuakes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class LargestQuakes {
    public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        //dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        System.out.println("The earthquake with the largest magnitude was: " + indexOfLargest(list));
        ArrayList<QuakeEntry> magnitudeList = getLargest(list, 50);
        for(QuakeEntry qe: magnitudeList){
            System.out.println(qe);
        }
    }
    
    public int indexOfLargest(ArrayList<QuakeEntry> data){
        int idx = 0;
        int maxIdx = 0;
        double maxMagnitude = 0;
        for(QuakeEntry qe: data){
            QuakeEntry quake = data.get(maxIdx);
            maxMagnitude = quake.getMagnitude();
            double currMagnitude = qe.getMagnitude();
            if(currMagnitude > maxMagnitude){
                maxIdx = idx;
            }
            idx ++;
        }
        return maxIdx;
    }
    
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        int maxMagnitude = 0;
        for(int i = 0; i < howMany; i++){
            maxMagnitude = indexOfLargest(copy);
            QuakeEntry qe = copy.get(maxMagnitude);
            answer.add(qe);
            copy.remove(qe);
        }
        return answer;
    }
}