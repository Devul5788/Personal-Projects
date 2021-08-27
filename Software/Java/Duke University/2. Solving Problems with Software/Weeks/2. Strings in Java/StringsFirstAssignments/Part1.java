
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public String findGene (String dna){
        String result = "";
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1){
            return "N/A";
        }
        int stopIndex = dna.indexOf("TAA", startIndex+3);
         if (stopIndex == -1){
            return "N/A";
        }
        if (startIndex-stopIndex % 3 == 0){
           result = dna.substring(startIndex, stopIndex+3);
           return result;
        }
        else {
            return "N/A";
        }
}
public void testFindGene(){
        String dna = "AAATGCCCTAACTAGATTAAGAAACC";
        System.out.println("DNA strand is: " + dna);
        String gene = findGene(dna);
        System.out.println("Gene is: " + gene);
        
        dna = "AAATGATAGAABANATATAGAA";
        System.out.println("DNA strand is: " + dna);
        gene = findGene(dna);
        System.out.println("Gene is: " + gene);
        
        dna = "AAATTTTTTTGGGGGAAAA";
        System.out.println("DNA strand is: " + dna);
        gene = findGene(dna);
        System.out.println("Gene is: " + gene);
        
        dna = "AAAAATGGATATAGTAGATAA";
        System.out.println("DNA strand is: " + dna);
        gene = findGene(dna);
        System.out.println("Gene is: " + gene);
        
        dna = "AAATGATATATATATAAGTAGTA";
        System.out.println("DNA strand is: " + dna);
        gene = findGene(dna);
        System.out.println("Gene is: " + gene);
    }}