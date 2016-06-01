package SeamCarving;
import javax.imageio.ImageIO;
import java.awt.image.WritableRaster;
import java.io.File;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Services {

    public static MyColor[][] getImage(String path) {

        try {

            File file = new File(path);
            BufferedImage bi = ImageIO.read(file);
            int h = bi.getHeight();
            int w = bi.getWidth();
            MyColor[][] result = new MyColor[w][h];


            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
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

    public static List<Seem> get_best_seem_list(int num_of_seems, double[][] dynamicMap) {
        List<Seem> seem_list = new LinkedList<>();
        double[] seem_scors = Arrays.copyOf(dynamicMap[dynamicMap.length - 1], dynamicMap[dynamicMap.length - 1].length);
        Arrays.sort(seem_scors);
        double max_seem_val = seem_scors[num_of_seems - 1];
        //we need all the seems with val<=max_seem_val

        for (int j = 0; j < dynamicMap.length; j++) {
            if (dynamicMap[dynamicMap.length - 1][j] <= max_seem_val) {
                seem_list.add(get_seem_starting_at(j, dynamicMap));
            }
            if (seem_list.size() == num_of_seems) {
                break;
            }

        }


        return seem_list;
    }

    private static Seem get_seem_starting_at(int seem_location, double[][] dynamicMap) {
        int height = dynamicMap.length;
        int seem_as_arr[] = new int[height];
        seem_as_arr[height - 1] = seem_location;
        for (int i = height - 2; i >= 0; i--) {
            int center = seem_as_arr[i + 1];
            double min = 1000000;
            int min_loc = -1;
            for (int width_diff = -1; width_diff <= 2; width_diff++) {
                try {
                    double min_cadidate = dynamicMap[i][center + width_diff];
                    if (min_cadidate < min) {
                        min = min_cadidate;
                        min_loc = center + width_diff;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }

            }
            seem_as_arr[i] = min_loc;
        }
        return new Seem(seem_as_arr);
    }


    public static MyColor[][] removeSeem(MyColor[][] old, Seem seemObj) {
        int[] seem = seemObj.seem_as_arr;
        int oldH = old.length;
        int oldW = old[0].length;

        int h = old.length;
        int w = old[0].length - 1;
        MyColor[][] newMat = new MyColor[h][w];
        for (int i = 0; i < old.length; i++) {
            boolean isRowAfterRemoval = false;
            for (int j = 0; j < old[0].length; j++) {
                if (seem[i] == j) {
                    isRowAfterRemoval = true;
                } else {
                    if (!isRowAfterRemoval) {
                        newMat[i][j] = old[i][j];
                    } else {
                        newMat[i][j - 1] = old[i][j];
                    }
                }
            }

        }
        int newH = newMat.length;
        int newW = newMat[0].length;
        return newMat;
    }

    public static void saveImage(MyColor[][] m, String path) {
//        File file = new File(path);
//        BufferedImage bi = ImageIO.read(pa);


        BufferedImage img = new BufferedImage(m.length, m[0].length,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                int r = (int) m[i][j].r; // red component 0...255
                int g = (int) m[i][j].g; // green component 0...255
                int b = (int) m[i][j].b; // blue component 0...255
                int col = (r << 16) | (g << 8) | b;
                img.setRGB(i, j, col);
            }
        }

        File f = new File(path);
        try {
            ImageIO.write(img, "JPEG", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
