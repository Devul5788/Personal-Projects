
/**
 * Write a description of Genes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class Genes {
    public String findGene(String dna) {
        String result = "";
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1){
            return "N/A";
        }
        int stopIndex = dna.indexOf("TAA", startIndex + 3);
        if (stopIndex == -1){
            return "N/A";
        }
        result = dna.substring(startIndex, stopIndex+3);
        return result;
    }
    public void testFindGene(){
        String dna = "AATGCGAATTAATCG";
        System.out.println("DNA strand is: " + dna);
        String gene = findGene(dna);
        System.out.println("Gene is: " + gene);
        
        dna = "CGATGGTTGATAAGCCTAAGCTATAA";
        System.out.println("DNA strand is: " + dna);
        gene = findGene(dna);
        System.out.println("Gene is: " + gene);
        
        dna = "CGATGGTTGATAAGCCTAAGCTAAA";
        System.out.println("DNA strand is: " + dna);
        gene = findGene(dna);
        System.out.println("Gene is: " + gene);
        
        dna = "ATGATAGTATATATATBEGBX";
        System.out.println("DNA strand is: " + dna);
        gene = findGene(dna);
        System.out.println("Gene is: " + gene);
    }
}
