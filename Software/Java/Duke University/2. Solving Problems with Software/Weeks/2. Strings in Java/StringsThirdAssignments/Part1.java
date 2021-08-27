
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class Part1 {
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
    
    public void testOn(String dna) {
        System.out.println("DNA Strand is: " + dna);
        StorageResource genes = getAllGenes(dna);
        for (String g: genes.data()){
            System.out.println("Gene is: " + g);
        }
    }
    
    public void test() {  
        testOn("ATGATCTAATTtATGCTGCAACGGTGAAGA");
        testOn("“AATGCTAACTAGCTGACTAAT”");
        testOn("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
        testOn("AATGGGGVVVBBBTAAATTTATGAAAAAATGATATGAAGGGGTTTTTATAGAAA");
    }
}









