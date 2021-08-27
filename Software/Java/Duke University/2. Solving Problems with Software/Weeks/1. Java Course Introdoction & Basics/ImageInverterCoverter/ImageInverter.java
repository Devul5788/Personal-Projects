
/**
 * Write a description of ImageInverter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
public class ImageInverter {
    public ImageResource makeInvert (ImageResource inImage){
        ImageResource outImage = new ImageResource (inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel: outImage.pixels()){
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            int invtR = 255-inPixel.getRed();
            int invtG = 255-inPixel.getGreen();
            int invtB = 255-inPixel.getBlue();
            pixel.setRed(invtR);
            pixel.setBlue(invtB);
            pixel.setGreen(invtG);
        }
        return outImage;
    }
    public void selectAndConvert(){
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles ()){
            ImageResource inImage = new ImageResource(f);
            ImageResource invert = makeInvert(inImage);
            String fname = inImage.getFileName();
            String nName = "InvertedImage - " + fname;
            invert.setFileName(nName);
            invert.draw();
            invert.save();
        }
}
}
