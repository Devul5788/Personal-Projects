
/**
 * Write a description of CSVLow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.io.File;
public class CSVLow {
    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord smallestSoFar = null;
        for (CSVRecord currentRow: parser){
            if (smallestSoFar == null){
                smallestSoFar = currentRow;
            }
            else{
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double lowestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
                if (currentTemp < lowestTemp){
                    smallestSoFar = currentRow;
                }
            }
        }
        return smallestSoFar;
    }
    
    public void testColdestHourInFile(){
        FileResource f = new FileResource();
        CSVRecord smallest = coldestHourInFile(f.getCSVParser());
        System.out.println("The coldest temperature was: " + smallest.get("TemperatureF") + 
                           " at " + smallest.get("DateUTC"));
    }
    
    public String fileWithColdestTemperature (){
        DirectoryResource dr = new DirectoryResource();
        CSVRecord smallestSoFar= null;
        String fileName = null;
        String filename = null;
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            if (smallestSoFar == null){
                smallestSoFar = currentRow;
            }
            else{
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
                if (currentTemp < coldestTemp && currentTemp > -9999){
                   smallestSoFar = currentRow;
                   fileName = f.getPath();
                   filename = f.getName(); 
                }
            }
        }
        System.out.println("Coldest temperature was in the file: " + filename);
        return fileName;
    }

    
    public void testFileWithColdestTemperature(){
        String filename = fileWithColdestTemperature ();
        FileResource fr = new FileResource(filename);
        CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
        CSVParser parser = fr.getCSVParser();
        System.out.println("The lowest Temperatures on the the coldest day was: " + smallest.get("TemperatureF") + 
                           " at " + smallest.get("DateUTC"));
        System.out.println("All the Temperatures on the the coldest day were: ");
        for (CSVRecord record: parser){
            String temp = record.get("TemperatureF");
            String Time = record.get("DateUTC");
            System.out.println(Time + " : " + temp);
        }              
    }
    
    public CSVRecord lowestHumididtyInFile(CSVParser parser){
        CSVRecord smallestSoFar = null;
        for (CSVRecord currentRow: parser){
            smallestSoFar = getSmallestOfTwo(currentRow, smallestSoFar);
        }
        return smallestSoFar;
    }
    
    public CSVRecord getSmallestOfTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
        if (smallestSoFar == null){
                smallestSoFar = currentRow;
        }
        else{
           if (!currentRow.get("Humidity").equals("N/A")){
               double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
               double lowestHumidity = Double.parseDouble(smallestSoFar.get("Humidity"));
               if (currentHumidity < lowestHumidity){
                        smallestSoFar = currentRow;
               }
           }
        }
        return smallestSoFar;
    }
    
    public void  testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumididtyInFile(parser);
        System.out.println("The lowest Humidity on the the day was: " + csv.get("Humidity") + 
                           " at " + csv.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles(){
        DirectoryResource dr = new DirectoryResource();
        CSVRecord smallestSoFar = null;
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource (f);
            CSVRecord currentRow = lowestHumididtyInFile(fr.getCSVParser());
            smallestSoFar = getSmallestOfTwo(currentRow, smallestSoFar);
        }
        return smallestSoFar;
    }
    
    public void testLowestHumidityInManyFiles(){
        DirectoryResource dr = new DirectoryResource();
        CSVRecord csv = lowestHumidityInManyFiles();
        System.out.println("The lowest Humidity on the the day was: " + csv.get("Humidity") + 
                           " at " + csv.get("DateUTC"));
    }
    
    public double averageTemperatureInFile(CSVParser parser){
        double average = 0;
        double total = 0;
        int count = 0;
        for (CSVRecord record: parser){
            total = Double.parseDouble(record.get("TemperatureF")) + total;
            count = count + 1;
            average = total/count;
        }
        return average;
    }
    
    public void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        double average = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("The Average Temperature of the Day was: " + average);
    } 
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double average = 0;
        double total = 0;
        int count = 0;
        double humidity = 0;
        for (CSVRecord currentRow: parser){
            if (!currentRow.get("Humidity").equals("N/A")){
                humidity = Double.parseDouble(currentRow.get("Humidity"));
                total = Double.parseDouble(currentRow.get("TemperatureF")) + total;
                if (humidity >= value){
                   count = count + 1;
                   average = total/count;
                }
            }
        }
        return average;
    }
    
    public void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        double average = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), 80);
        if (average > 0){
            System.out.println("The Average Temperature when High Humidity is: " + average);
        }
        else {
            System.out.println("No temperatures with high humidity was found!");
        }
    }
}
