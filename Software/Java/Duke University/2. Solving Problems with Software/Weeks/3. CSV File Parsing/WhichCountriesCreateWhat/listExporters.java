
/**
 * Write a description of listExporters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class listExporters {
    public void tester (){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println(countryInfo(parser, "India"));
        parser = fr.getCSVParser();
        listExportersTwoProducts(parser, "cotton", "flowers");
        parser = fr.getCSVParser();
        System.out.println("The Number of Countries That Export exportItem: " + numberOfExporters(parser, "cocoa"));
        parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999,999");
    }
    
    public String countryInfo(CSVParser parser, String country){
        String countryinfo = "";
        for (CSVRecord record: parser){
            String Country = record.get("Country");
            if (Country.contains(country)){
                String exports = record.get("Exports");
                String value = record.get("Value (dollars)");
                countryinfo = Country + ": " + exports + ": " + value;
                return countryinfo;
            }
        }
        return "NOT FOUND!";
    }
    
     public void listExportersTwoProducts (CSVParser parser, String exportitem1, String exportitem2){
        for (CSVRecord record: parser){
            String exports = record.get("Exports");
            if (exports.contains(exportitem1) && exports.contains(exportitem2)){
               String Country = record.get("Country");
               System.out.println(Country);
            }
        }
    }
    
    public int numberOfExporters (CSVParser parser, String exportItem){
        int count = 0;
        for (CSVRecord record: parser){
            String exports = record.get("Exports");
            if(exports.contains(exportItem)){
                count = count + 1;
            }
        }
        return count;
    }
    
    public void bigExporters(CSVParser parser, String amount){
        for (CSVRecord record: parser){
            String values = record.get("Value (dollars)");
            if (values.length()> amount.length()){
                String Country = record.get("Country");
                System.out.println(Country + ": " + values);
            }
        }
    }
}