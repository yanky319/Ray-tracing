package unittests;

import org.junit.Test;
import renderer.ImageWriter;

import java.awt.*;

import static org.junit.Assert.*;

public class ImageWriterTest {

    @Test
    public void testWriteToImage(){
        ImageWriter imageWriter =
                new ImageWriter("write to image test",1600,100,800,500);
        for(int i = 0; i<imageWriter.getNy();i++){
            for(int j = 0; j < imageWriter.getNx(); j++){
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(j, i, Color.GREEN);
                } else {
                    imageWriter.writePixel(j, i, Color.MAGENTA);
                }
            }
        }
        imageWriter.writeToImage();
    }
}