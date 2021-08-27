
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    public String findGene (String dna, int startCodon, int stopCodon){
        String result = "";
        if (startCodon == -1){
            return "N/A";
        }
         if (stopCodon == -1){
            return "N/A";
        }
        if (startCodon-stopCodon % 3 == 0){
           result = dna.substring(startCodon, stopCodon+3);
           return result;
        }
        else {
            return "N/A";
        }
}
public void testFindGene(){
        String dna = "ATATATATTTAAA";
        System.out.println("DNA strand is: " + dna);
        int startCodon = dna.indexOf("ATG");
        int stopCodon = dna.indexOf("TAA", startCodon+3);
        String gene = findGene(dna, startCodon, stopCodon);
        System.out.println("Gene is: " + gene);
        
        dna = "AAATGATAGAABANATATAGAA";
        System.out.println("DNA strand is: " + dna);
        startCodon = dna.indexOf("ATG");
        stopCodon = dna.indexOf("TAA", startCodon+3);
        gene = findGene(dna, startCodon, stopCodon);
        System.out.println("Gene is: " + gene);
        
        dna = "AAATTTTTTTGGGGGAAAA";
        System.out.println("DNA strand is: " + dna);
        startCodon = dna.indexOf("ATG");
        stopCodon = dna.indexOf("TAA", startCodon+3);
        gene = findGene(dna, startCodon, stopCodon);
        System.out.println("Gene is: " + gene);
        
        dna = "AAAAATGGATATAGTAGATAA";
        System.out.println("DNA strand is: " + dna);
        startCodon = dna.indexOf("ATG");
        stopCodon = dna.indexOf("TAA", startCodon+3);
        gene = findGene(dna, startCodon, stopCodon);
        System.out.println("Gene is: " + gene);
        
        dna = "AAATGATATATATATAAGTAGTA";
        System.out.println("DNA strand is: " + dna);
        startCodon = dna.indexOf("ATG");
        stopCodon = dna.indexOf("TAA", startCodon+3);
        gene = findGene(dna, startCodon, stopCodon);
        System.out.println("Gene is: " + gene);
        
        String dna1 = "atgctataa";
        dna = dna1.toUpperCase();
        System.out.println("DNA strand is: " + dna);
        startCodon = dna.indexOf("ATG");
        stopCodon = dna.indexOf("TAA", startCodon+3);
        gene = findGene(dna, startCodon, stopCodon);
        System.out.println("Gene is: " + gene);
    }}