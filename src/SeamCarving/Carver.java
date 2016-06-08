package SeamCarving;
import javax.imageio.ImageIO;
import java.awt.color.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.List;



public class Carver {

    public static void main(String [] args) throws IOException {
        String input_image_filename = args[0];
        int num_of_colums = Integer.parseInt(args[1]);
        int num_of_rows = Integer.parseInt(args[2]);
        int energy_type = Integer.parseInt(args[3]);
        String output_image_filename = args[4];


        /*
        MyColor[][] img = new MyColor[5][5];
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                img[i][j] = new MyColor(0.5, 0.5, 0.5);
            }
        }
        img[2][2] = new MyColor(1,1,1);
        img[2][1] = new MyColor(1,1,1);
        img[2][3] = new MyColor(1,1,1);*/

       MyColor[][] img = Services.getImage(input_image_filename);

        long start = System.nanoTime();

        int width = img[0].length;
        int height = img.length;

        img = remove_seams(img, width-num_of_colums, energy_type);
        img = Services.transpose(img);
        img = remove_seams(img, height-num_of_rows, energy_type);
        img = Services.transpose(img);


        long a = 1000000000;
        a *= 60;
        System.out.print("time: ");
        System.out.println((System.nanoTime()-start)/a);

        Services.saveImage(img, output_image_filename);

        return;
    }

    private static MyColor[][] remove_seams(MyColor[][] img, int num_of_seams_ro_remove, int energy_type) {
        for (int i = 0; i <num_of_seams_ro_remove ; i++) {

            int width = img[0].length;
            int height = img.length;

            System.out.println(i); // todo- delete
            double[][] dynamic = Energizer.energy(img, energy_type);
            List<Seem> seems = Services.get_best_seem_list(1, dynamic);

            img = Services.removeSeem(img, seems.get(0));


        }
        return img;
    }


}

