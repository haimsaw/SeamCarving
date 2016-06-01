package SeamCarving;
import javax.imageio.ImageIO;
import java.io.File;

import java.awt.image.BufferedImage;
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

    public static List<Seem> get_best_seem_list(int num_of_seems, double[][] dynamicMap){
        List<Seem> seem_list = new LinkedList<>();
        double[] seem_scors = Arrays.copyOf(dynamicMap[dynamicMap.length-1], dynamicMap[dynamicMap.length-1].length);
        Arrays.sort(seem_scors);
        double max_seem_val = seem_scors[num_of_seems - 1];
        //we need all the seems with val<=max_seem_val

        for (int j=0; j<dynamicMap.length; j++){
            if (dynamicMap[dynamicMap.length -1][j]<=max_seem_val){
                seem_list.add(get_seem_starting_at(j, dynamicMap));
            }
            if (seem_list.size() == num_of_seems){
                break;
            }

        }


        return seem_list;
    }

    private static Seem get_seem_starting_at(int seem_location, double[][] dynamicMap) {
        int height = dynamicMap[0].length;
        int seem_as_arr[] = new int[height];
        seem_as_arr[height-1] = seem_location;
        for (int i = height-2; i>=0; i--){
            int center = seem_as_arr[i+1];
            double min = 1000000;
            int min_loc = -1;
            for (int width_diff = -1; width_diff<=2;width_diff++){
                try {
                    double min_cadidate = dynamicMap[i][center + width_diff];
                    if (min_cadidate < min){
                        min = min_cadidate;
                        min_loc = center + width_diff;
                    }
                }
                catch (ArrayIndexOutOfBoundsException e){
                    continue;
                }

            }
            seem_as_arr[i] = min_loc;
        }
        return new Seem(seem_as_arr);
    }
}
