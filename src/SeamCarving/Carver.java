package SeamCarving;
import javax.imageio.ImageIO;
import java.awt.color.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;


public class Carver {

    public static void main(String [ ] args){
        String input_image_filename = args[0];
        int num_of_colums = Integer.parseInt(args[1]);
        int num_of_rows = Integer.parseInt(args[2]);
        int energy_type = Integer.parseInt(args[3]);
        String output_image_filename = args[4];


        MyColor[][] img = GetImage(input_image_filename);

        double[][] map =  Energizer.energy(img);
        return ;
    }

    private static MyColor[][] GetImage(String input_image_filename) {
        MyColor[][] ret = new  MyColor[5][5];
        for (int i = 0;i<5;i++){
            for (int j = 0;j<5;j++){
                ret[i][j] = new MyColor(0.5,0.5,0.5);
            }
        }
        ret[]
        return ret;
    }

}

