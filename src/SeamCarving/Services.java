package SeamCarving;
import javax.imageio.ImageIO;
import java.awt.color.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import java.awt.image.BufferedImage;

public class Services {

    public static MyColor[][] getImage(String path) {

        try {
            File file = new File(path);
            BufferedImage bi = ImageIO.read(file);
            int h = bi.getHeight();
            int w = bi.getWidth();
            int[][] result = new int[h][w];
            int[] oneDimentionalArray = new int[w*h];

            for (int i=0; i<h; i++) {
                for (int j=0; j<w; j++) {
                    int rgb = bi.getRGB(i,j);
                    double red = (rgb >> 16) & 0xFF;
                    double green = (rgb >> 8) & 0xFF;
                    double blue = (rgb      ) & 0xFF;
                    result[i][j] = new MyColor(red, green, blue);
            }

            return result;
        } catch (Exception e) {
            System.out.println("ERROR");
            return null;
        }

    }
}
