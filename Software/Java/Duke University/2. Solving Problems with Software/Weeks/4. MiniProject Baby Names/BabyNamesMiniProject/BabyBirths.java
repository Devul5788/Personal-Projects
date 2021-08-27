
/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
public class BabyBirths {
    public void printNames (){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord record: parser){
            int numBorn = Integer.parseInt(record.get(2));
            if (numBorn >= 100){
                System.out.println("Name: " + record.get(0) + " Gender: " + record.get(1)
                                   + " Number: " + record.get(2));
            }
        }
    }
    
    public void totalBirths(FileResource fr){
        int numBorn = 0;
        int totalBirths = 0;
        int totalgirls = 0;
        int totalboys = 0;
        for (CSVRecord rec: fr.getCSVParser(false)){
            numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("F")){
                totalgirls += 1;
            }
            else{
                totalboys += 1;
            }
        }
        System.out.println("The total number of births are = " + totalBirths);
        System.out.println("The total number of girls are = " + totalgirls);
        System.out.println("The total number of boys are = " + totalboys);
    }
    
    public void testTotalBirths(){
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    
    public int getRank (int year, String name, String gender){
        int rank = 0;
        int rank2 = 0;
        FileResource fr = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");
        for (CSVRecord rec: fr.getCSVParser(false)){ 
            if (rec.get(1).equals(gender)){
                if (rec.get(1).equals("F")){
                    rank += 1;
                }
                else {
                    rank += 1;
                } 
                String recName = rec.get(0);
                String recGender = rec.get(1);
                if (recName.equals(name) && recGender.equals(gender)){
                    return rank;
                }
                else {
                    rank2 = -1;
                }
            }
        }
        if (rank2 == -1){
            rank = -1;
        }
        return rank;
    }
    
    public void testgetRank(){
        String name = "Frank";
        String gender = "M";
        int year = 1971;
        int rank = getRank(year, name, gender);
        if (gender.equals("F")){
            gender = "female";
        }
        else {
            gender = "male";
        }
        if (rank != -1 && rank != 0){
            System.out.println("The rank of the " + gender + " baby with the name " + name + " is " + rank
                                + " in the year of "+ year);
        }
        else {
            System.out.println("No " + gender + " baby was found with the name " + name
                                + " in the year of "+ year);
        }
    }
    
    public String getName (int year, int rank, String gender){
        int mRank = 0;
        int fRank = 0;
        String name = "";
        String name1 = "";
        FileResource fr = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");
        for (CSVRecord rec: fr.getCSVParser(false)){
            if (rec.get(1).equals(gender)){
                if (rec.get(1).equals("F")){
                    fRank += 1;
                    if (fRank == rank){
                        name = rec.get(0);
                        return name;
                    }
                    else{
                        name1 = "No name was found at the given rank";
                    }
                }
                else {
                    mRank += 1;
                    if (mRank == rank){
                        name = rec.get(0);
                        return name;
                    }   
                    else{
                        name1 = "No name was found at the given rank";
                    }
                } 
            }
        }
        if (name1.equals("No name was found at the given rank")){
            name = name1;
        }
        return name;
    }
    
    public void testgetName(){
        String gender = "M";
        int year = 1982;
        int rank = 450;
        String name = getName(year, rank, gender);
        if (gender.equals("F")){
            gender = "female";
        }
        else {
            gender = "male";
        }
        if (name.equals("No name was found at the given rank")){
            System.out.println(name + " in the year of "+ year);
        }
        else {
            System.out.println("The " + gender + " baby with the rank " + rank + " has a name of " + name + " in the year of " + year);
        }
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int rankInYear = getRank(year, name, gender);
        String nameInNewYear = getName(newYear, rankInYear, gender);
        System.out.println(name + " born in " + year + " would be named " + nameInNewYear + " if he/she was born in " + newYear + ".");
    }
    
    public void testwhatIsNameInYear(){
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    public int yearOfHighestRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        int highestRank = 0;
        int rankInFile = 0;
        int currentYear = 0;
        int HighestYear = 0;
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource (f);
            currentYear = Integer.parseInt(f.getName().substring(3,7));
            rankInFile = getRank(currentYear, name, gender);
            if (highestRank == 0){
                if (rankInFile != 0 && rankInFile != -1){
                    highestRank = rankInFile;
                }
            }
            if (rankInFile < highestRank){
                if (rankInFile != 0 && rankInFile != -1){
                    highestRank = rankInFile;
                    HighestYear = currentYear;
                }
            }
        }
        if (highestRank == -1){
            return -1;
        }
        return HighestYear;
    }
    
    public void testyearOfHighestRank(){
        String name = "Mich";
        String gender = "M";
        int year = yearOfHighestRank(name, gender);
        int rank = 0;
        if (year != -1 && year != 0){
            rank = getRank(year, name, gender);
        }
        if (gender.equals("F")){
            gender = "female";
        }
        else {
            gender = "male";
        }
        if (year != -1 && rank != -1 && year != 0 && rank != 0){
            System.out.println("The " + gender + " baby name " + name + " has the highest name rank of " + rank + 
                                " in the year " + year);
        }
        else {
            System.out.println("No " + gender + " baby was found with the name " + name
                                + " in any of the selected years ");
        }
    }
    
    public double getAverageRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        double average = 0.00;
        double total = 0;
        int files = 0;
        int currentYear = 0;
        int rankInFile = 0;
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource (f);
            currentYear = Integer.parseInt(f.getName().substring(3,7));
            rankInFile = getRank(currentYear, name, gender);
            if (rankInFile != 0 && rankInFile != -1){
                total = rankInFile+ total;
                files = files + 1;
            }
        }
        if (files == 0 || files == -1 || total == 0 || total == -1){
            return -1.00;
        }
        average = total/files;
        return average;
    }
    
    public void testgetAverageRank(){
        String name = "Robert";
        String gender = "M";
        double average = getAverageRank(name, gender);
        if (gender.equals("F")){
            gender = "female";
        }
        else {
            gender = "male";
        }
        if (average != -1.0 && average != 0){
            System.out.println("The " + gender + " baby name " + name + " has the average name rank of " + average + 
                                " in the selected files.");
        }
        else {
            System.out.println("No " + gender + " baby was found with the name " + name
                                + " in any of the selected years ");
        }
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        FileResource fr = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");
        int nameRank = 0;
        int total = 0;
        int currentNumber = 0;
        int currentRank = 0;
        int Number = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                nameRank = getRank(year, name, gender);
                currentRank = getRank(year, rec.get(0), gender);
                if (currentRank < nameRank){
                    currentNumber = Integer.parseInt(rec.get(2));
                    total = total + currentNumber;
                }
            }
            Number = Number + 1;
            if (Number == nameRank){
               break;
            }
        }
        return total;
    }
    
    public void testGetTotalBirthsRankedHigher(){
        System.out.println(getTotalBirthsRankedHigher(1990, "Drew", "M"));
    }
}
