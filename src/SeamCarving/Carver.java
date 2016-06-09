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

    private static MyColor[][] add_seams(MyColor[][] img, int num_of_seams_to_add, int energy_type) {

        int width = img[0].length;
        int height = img.length;

        int[][][] pointersMatrix = new int[height][width][2];
        for (int i=0; i<pointersMatrix.length; i++ ) {
            for (int j=0; j< pointersMatrix[0].length; j++) {
                pointersMatrix[i][j][0] = i;
                pointersMatrix[i][j][1] = j;
            }
        }

        MyColor[][] imgCopy = Services.copyImage(img);

        for (int i = 0; i <num_of_seams_to_add ; i++) {
            double[][] dynamic = Energizer.energy(img, energy_type);
            List<Seem> seems = Services.get_best_seem_list(1, dynamic);
            img = Services.removeSeem(img, seems.get(0));
            pointersMatrix = Services.removeSeemFromPointersMatrix(pointersMatrix, seems.get(0));
        }


//        int sum = 0;
//        int m;
//        for (m = 0; m< pixelsToAdd[0].length; m++) {
//            sum += pixelsToAdd[0][m];
//        }
//        System.out.println(sum);

        MyColor[][] newImg = addAndMultiplyPixels(imgCopy, pointersMatrix);

        return newImg;
    }

    private static MyColor[][] addAndMultiplyPixels(MyColor[][] img, int[][][] pointersMatrix) {
        int numOfSeems = img[0].length - pointersMatrix[0].length;
        MyColor[][] bigger = new MyColor[img.length][img[0].length + numOfSeems];

        for (int i = 0; i < img.length; i++) {
            int alreadyAdded = 0;
            for (int j = 0; j < img[0].length; j++) {
                boolean seemWasRemoved = true;
                for (int w = 0; w < pointersMatrix[0].length; w++) {
                    if (pointersMatrix[i][w][0] == i && pointersMatrix[i][w][1] == j) { //no need to multiply
                        seemWasRemoved = false;
                    }
                }
                if (!seemWasRemoved) {
                    MyColor c = img[i][j];
                    bigger[i][j + alreadyAdded] = new MyColor(c.r, c.g, c.b);
                } else {
                    MyColor c = img[i][j];
                    bigger[i][j + alreadyAdded] = new MyColor(c.r, c.g, c.b);
                    bigger[i][j + alreadyAdded + 1] = new MyColor(c.r, c.g, c.b);
                    alreadyAdded += 1;
                }

            }
        }
        return bigger;
    }





//        for (int i=0; i<img.length; i++) {
//            int numOfAlreadyAdded = 0;
//            for (int j=0; j<img[0].length; j++) {
//                if (pixelsToAdd[i][j] == 1) {
//                    MyColor c = img[i][j];
//                    newImg[i][j+numOfAlreadyAdded] = new MyColor(c.r, c.g, c.b);
//                    newImg[i][j+numOfAlreadyAdded+1] = new MyColor(c.r, c.g, c.b);
//                    numOfAlreadyAdded += 1;
//                } else {
//                    MyColor c = img[i][j];
//                    newImg[i][j+numOfAlreadyAdded] = new MyColor(c.r, c.g, c.b);
//                }
//            }
//            if (numOfAlreadyAdded != numOfSeems) {
//                System.out.println("Problem");
////            }
//        }
//        return newImg;
//    }

}

