package SeamCarving;
import javax.imageio.ImageIO;
import java.io.File;

import java.awt.image.BufferedImage;
import java.util.List;

public class Services {

    public static MyColor[][] getImage(String path) {

        try {

            File file = new File(path);
            BufferedImage bi = ImageIO.read(file);
            int h = bi.getHeight();
            int w = bi.getWidth();
            MyColor[][] result = new MyColor[w][h];


            for (int i=0; i<w; i++) {
                for (int j=0; j<h; j++) {
                    int rgb = bi.getRGB(i, j);
                    double red = (rgb >> 16) & 0xFF;
                    double green = (rgb >> 8) & 0xFF;
                    double blue = (rgb) & 0xFF;
                    result[i][j] = new MyColor(red, green, blue);
                }
            }

            return result;
        } catch (Exception e) {
            System.out.println("ERROR");
            return null;
        }

    }

    public List<Seem> get_best_seem_list(int num_of_seems, float[] dynamicMap){
        
    }
}
