package SeamCarving;
import javax.imageio.ImageIO;
import java.awt.color.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;


public class Carver {

    public static void main(String [ ] args){
        String  input_image_filename = args[0];




        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(args[1]));
        } catch (IOException e) {
            System.out.println("ERROR");
        }

        byte[][][] img = null;

    }
}
