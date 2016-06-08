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


        MyColor[][] img = Services.getImage(input_image_filename);

//        MyColor[][] img = new MyColor[7][7];
//        for (int i=0; i<img.length; i++) {
//            for (int j=0; j<img[0].length; j++) {
//                img[i][j] = new MyColor(1,1,1);
//            }
//        }
//        img[0][0] = new MyColor(2,2,2);
//        img[1][1] = new MyColor(2,2,2);
//        img[2][2] = new MyColor(2,2,2);
//        img[3][3] = new MyColor(2,2,2);
//        img[4][4] = new MyColor(2,2,2);
//        img[5][5] =  new MyColor(2,2,2);
//        img[6][6] =  new MyColor(3,3,3);

        long start = System.nanoTime();

        int width = img[0].length;
        int height = img.length;


        if (width > num_of_colums) {
            img = remove_seams(img, width - num_of_colums, energy_type);
        } else if (width < num_of_colums) {
            img = add_seams(img, num_of_colums - width , energy_type);
        }

        if (height > num_of_rows) {
            img = Services.transpose(img);
            img = remove_seams(img, height - num_of_rows, energy_type);
            img = Services.transpose(img);
        } else if (height < num_of_rows) {
            img = Services.transpose(img);
            img = add_seams(img, num_of_rows - height, energy_type);
            img = Services.transpose(img);
        }

        long a = 1000000000;
        a *= 60;
        System.out.print("time: ");
        System.out.println((System.nanoTime()-start)/a);

        Services.saveImage(img, output_image_filename);

        return;
    }

    private static MyColor[][] remove_seams(MyColor[][] img, int num_of_seams_ro_remove, int energy_type) {
        int width = img[0].length;
        int height = img.length;
        int[][] pixelsToAdd = null;

        for (int i = 0; i <num_of_seams_ro_remove ; i++) {

            System.out.println(i); // todo- delete
            double[][] dynamic = Energizer.energy(img, energy_type);
            List<Seem> seems = Services.get_best_seem_list(1, dynamic);

            img = Services.removeSeem(img, seems.get(0), pixelsToAdd, i, false);


        }
        return img;
    }

    private static MyColor[][] add_seams(MyColor[][] img, int num_of_seams_to_add, int energy_type) {

        int width = img[0].length;
        int height = img.length;
        int[][] pixelsToAdd = new int[height][width];

        MyColor[][] imgCopy = Services.copyImage(img);

        for (int i = 0; i <num_of_seams_to_add ; i++) {
            double[][] dynamic = Energizer.energy(img, energy_type);
            List<Seem> seems = Services.get_best_seem_list(1, dynamic);
            img = Services.removeSeem(img, seems.get(0), pixelsToAdd, i, true);
        }


        int sum = 0;
        int m;
        for (m = 0; m< pixelsToAdd[0].length; m++) {
            sum += pixelsToAdd[0][m];
        }
        System.out.println(sum);

        MyColor[][] newImg = addAndMultiplyPixels(imgCopy, pixelsToAdd, num_of_seams_to_add);

        return newImg;
    }

    private static MyColor[][] addAndMultiplyPixels(MyColor[][] img, int[][] pixelsToAdd, int numOfSeems) {
        MyColor[][] newImg = new MyColor[img.length][img[0].length+numOfSeems];

        for (int i=0; i<img.length; i++) {
            int numOfAlreadyAdded = 0;
            for (int j=0; j<img[0].length; j++) {
                if (pixelsToAdd[i][j] == 1) {
                    MyColor c = img[i][j];
                    newImg[i][j+numOfAlreadyAdded] = new MyColor(c.r, c.g, c.b);
                    newImg[i][j+numOfAlreadyAdded+1] = new MyColor(c.r, c.g, c.b);
                    numOfAlreadyAdded += 1;
                } else {
                    MyColor c = img[i][j];
                    newImg[i][j+numOfAlreadyAdded] = new MyColor(c.r, c.g, c.b);
                }
            }
//            if (numOfAlreadyAdded != numOfSeems) {
//                System.out.println("Problem");
//            }
        }
        return newImg;

    }

}

