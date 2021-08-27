import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe: quakeData){
            if(qe.getMagnitude() > magMin){
                answer.add(qe);
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax, Location from) {
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(int i = 0; i < copy.size(); i++){
            QuakeEntry quake = copy.get(i);
            Location loc = quake.getLocation();
            double dist = loc.distanceTo(from);
            if(dist/1000 <= distMax){
                answer.add(quake);
            }
        }
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        ArrayList<QuakeEntry> bigList = filterByMagnitude(list, 5.0);
        System.out.println("read data for "+list.size()+" quakes");
        for(QuakeEntry qe: bigList){
            System.out.println(qe);
        }
        System.out.println("There are " + bigList.size() + " quakes that match the criteria");
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        // This location is Durham, NC
        Location city = new Location(35.988, -78.907);
        // This location is Bridgeport, CA
        //Location city =  new Location(38.17, -118.82);
        ArrayList<QuakeEntry> closeList  = filterByDistanceFrom(list, 1000.00, city);
        for(QuakeEntry qe: closeList){
            double dist = qe.getLocation().distanceTo(city)/1000;
            System.out.println(dist + " " + qe.getInfo());
        }
        System.out.println("Found " + closeList.size() + " quakes that match that criteria");
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        //dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry>quakeData, double minDepth, double maxDepth){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        System.out.println("Find quakes with depth between " + minDepth + " and " + maxDepth);
        for(QuakeEntry qe: quakeData){
            if(qe.getDepth() > minDepth && qe.getDepth() < maxDepth){
                answer.add(qe);
            }
        }
        return answer;
    }
    
    public void quakesOfDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> depthList = filterByDepth(list, -4000.0, -2000.0);
        for(QuakeEntry qe: depthList){
            System.out.println(qe);
        }
        System.out.println("There are " + depthList.size() + " quakes that match the criteria");
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry>quakeData, String where, String phrase){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe: quakeData){
            String s = qe.toString();
            if(where.equals("end") && qe.getInfo().endsWith(phrase)){
                answer.add(qe);
            }
            if(where.equals("start") && qe.getInfo().startsWith(phrase)){
                answer.add(qe);
            }
            int idx = s.indexOf(phrase);
            if(where.equals("any") && idx != -1){
                answer.add(qe);
            }
        }
        return answer;
    }
    
    public void quakesByPhrase(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        String where = "any";
        String phrase = "Can";
        ArrayList<QuakeEntry> filteredPhraseList = filterByPhrase(list, where, phrase);
        for(QuakeEntry qe: filteredPhraseList){
            System.out.println(qe);
        }
        System.out.println("There are " + filteredPhraseList.size() + " quakes that match " + phrase + " at " + where);
    }
}
