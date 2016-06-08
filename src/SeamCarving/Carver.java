package SeamCarving;
import javax.imageio.ImageIO;
import java.awt.color.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.List;



public class Carver {

    public static void main(String [] args) {
        String input_image_filename = args[0];
        int num_of_colums = Integer.parseInt(args[1]);
        int num_of_rows = Integer.parseInt(args[2]);
        int energy_type = Integer.parseInt(args[3]);
        String output_image_filename = args[4];


        MyColor[][] img = Services.getImage(input_image_filename);

        /*
        MyColor[][] img = new MyColor[5][5];
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                img[i][j] = new MyColor(0.5, 0.5, 0.5);
            }
        }
        MyColor[][] img = Services.transpose(Services.getImage(input_image_filename));

        img[2][2] = new MyColor(1,1,1);*/



        MyColor[][] result = img.clone();


        int h;
        int w;
        long start = System.nanoTime();
//        for (int i = 0; i < 100; i++) {
//            System.out.println(i);
//            double[][] energy = Energizer.energy1(result);

        h = result.length;
        w = result[0].length;

        for (int i = 0; i < 300; i++) {
            double[][] energy = Energizer.energy0(result);

            double[][] dynamic = Energizer.createDynamicMap(energy);
            List<Seem> seems = Services.get_best_seem_list(1, dynamic);

            result = Services.removeSeem(result, seems.get(0));
            h = result.length;
            w = result[0].length;


        }
        long a = 1000000000;
        a *= 60;
        System.out.println((System.nanoTime()-start)/a);

        Services.saveImage(result, output_image_filename);

        return;
    }


}

