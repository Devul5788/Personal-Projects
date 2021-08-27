
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class Part3 {
    public void ProcessGenes (StorageResource sr){
        int nCount = 0;
        int rCount = 0;
        int geneLength = 0;
        int CTGcount = 0;
        String longestGene = "";
        
        for (String d: sr.data()){
            if(d.length()>60){
                System.out.println("DNA strand longer than 60 characters: " + d);
                nCount = nCount + 1;
            }
        }
        System.out.println("Number of DNA strands longer than 60 characters: " + nCount);
        for (String d: sr.data()){
            double ratio = cgRatio(d); 
            if (ratio>0.35){
                System.out.println("DNA strand that have a higher CG ration than 0.35: " + d);
                rCount = rCount + 1;
            }
        }
        System.out.println("Number of DNA strands that have a higher CG ration than 0.35: " + rCount);
        for (String d: sr.data()){
            if (d.length() > geneLength){
                geneLength = d.length();  
                longestGene = d;
            }
        }
        System.out.println("The Longest Gene in the DNA strand is: " + longestGene);
        System.out.println("The Length of the Longest Gene in the DNA strand is: " + geneLength);
        for (String d: sr.data()){
            int CTGIndex = d.indexOf("CTG");
            while (true){
            if (CTGIndex == -1){
                break;
            }
            CTGIndex = d.indexOf("CTG", CTGIndex+1);
            CTGcount = CTGcount+1;
            }
        }
        System.out.println("The Number of Times CTG Appears on the DNA Strand: " + CTGcount);
    }
    
     public double cgRatio (String dna){
        int cCount = 0;
        int gCount = 0;
        int cIndex = dna.indexOf("C");
        int gIndex = dna.indexOf("G");
        while (true){
             if (cIndex == -1 || gIndex == -1 || cIndex > dna.length()-1 || gIndex > dna.length()-1){
                 break;
             }
             cCount = cCount+1;
             gCount = gCount+1;;
             cIndex = dna.indexOf("C", cIndex + 1);
             gIndex = dna.indexOf("G", gIndex + 1);
        }
        int cgCount = cCount+gCount;
        double stringLength = dna.length();
        double answer = cgCount/stringLength;
        //System.out.println("This is the ratio of C:G in dna: " + answer);
        return answer;
    }
    
    public void testProcessGenesFromFile(){
        URLResource fr = new URLResource("https://users.cs.duke.edu/~rodger/GRch38dnapart.fa");
        String dna = fr.asString().toUpperCase();
        System.out.println("DNA is: " + dna);
        StorageResource geneList = getAllGenes(dna);
        ProcessGenes(geneList);
    }
    
    public int findStopCodon(String dna, int startIndex, String stopCodon){
        int currIndex = dna.indexOf(stopCodon, startIndex+3);
        while (currIndex != -1){
            int diff = currIndex - startIndex;
            if (diff % 3 == 0){
                return currIndex;
            }
            else {
              currIndex = dna.indexOf(stopCodon, currIndex+1);
            }
        }
        return  -1;
    }
    
    public String findGene(String dna, int where){
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1){
            return "";
        }
        int taaIndex = findStopCodon (dna, startIndex, "TAA");
        int tagIndex = findStopCodon (dna, startIndex, "TAG");
        int tgaIndex = findStopCodon (dna, startIndex, "TGA");
        int minIndex = 0;
        if (taaIndex == -1 || (tgaIndex != -1 && tgaIndex < taaIndex)){
            minIndex = tgaIndex;
        }
        else{
            minIndex = taaIndex;
        }
        if (minIndex == -1 || (tagIndex != -1 && tagIndex < minIndex)){
            minIndex = tagIndex;
        }
        if (minIndex == -1){
            return "";
        }
        return dna.substring(startIndex, minIndex + 3);
    }
    
    public StorageResource getAllGenes(String dna){
        int startIndex = 0;
        int count = 0;
        StorageResource geneList = new StorageResource();
        while (true){
            String currentGene = findGene(dna, startIndex);
            if (currentGene.isEmpty()){
                break;
            }
            geneList.add(currentGene);
            startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length();
            count = count + 1;
        }
        System.out.println("Number of Genes is/are: " + count);
        return geneList;
    }  
}
