package SeamCarving;


import java.util.InputMismatchException;

public class Energizer {

    public static double[][] energy0(MyColor[][] image){
        // not entropy
        int width = image[0].length;
        int height = image.length;
        double[][] energyMap = new double[height][width];

        for (int i=0;i<height;i++){
            for (int j=0;j<width;j++){
                int num_of_nebors = 0;
                double energy = 0;

                for (int height_diff = -1; height_diff<=1;height_diff++){
                    for (int width_diff = -1; width_diff<=1; width_diff++){
                        if (width_diff==0 && height_diff==0){
                            continue;
                        }
                        try {
                            energy += image[i+height_diff][j+width_diff].norma1(image[i][j]);
                            num_of_nebors ++;
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            continue;
                        }

                    }
                }

                energyMap[i][j] = energy/num_of_nebors;

            }
        }

        return energyMap;
    }

    public static double[][] energy1(MyColor[][] image){
        //0+ entropy
        int width = image[0].length;
        int height = image.length;
        double[][] energyMap = new double[height][width];

        double[][] energy0 = energy0(image);
        double[][] ro_ln_ro = roLnRo(gray_scale_image(image));


        for (int i=0;i<height;i++) {
            for (int j = 0; j < width; j++) {
                int num_of_nebors = 0;
                double neibors_ro_ln_ro = 0;

                for (int height_diff = -4; height_diff <= 4; height_diff++) {
                    for (int width_diff = -4; width_diff <= 4; width_diff++) {
                        try{
                            double neibor_ro_ln_ro = ro_ln_ro[i+height_diff][j+width_diff];
                            neibors_ro_ln_ro += neibor_ro_ln_ro;
                            num_of_nebors ++;
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            continue;
                        }

                    }
                }
                energyMap[i][j] = -1*0.5*neibors_ro_ln_ro/num_of_nebors+ 0.5*energy0[i][j];

            }
        }

        return energyMap;
    }

    private static double[][] roLnRo(double[][] grays) {
        int width = grays[0].length;
        int height = grays.length;
        double[][] ro_ln_ro = new double[height][width];
        for (int i=0;i<height;i++) {
            for (int j = 0; j < width; j++) {
                int num_of_naibors = 0;
                double sum_of_grays = 0;
                for (int height_diff = -4; height_diff<=4;height_diff++) {
                    for (int width_diff = -4; width_diff <= 4; width_diff++) {

                        try {
                            double gray = grays[i + height_diff][j + width_diff];
                            num_of_naibors++;
                            sum_of_grays += gray;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
                double ro = grays[i][j]/sum_of_grays; //todo- need to normalize by num of neibors?
                ro_ln_ro[i][j] = ro*Math.log(ro);
                if (grays[i][j] == 0.0 ){
                    ro_ln_ro[i][j] = Double.MIN_VALUE;
                }
                else if (sum_of_grays == 0){
                    ro_ln_ro[i][j] = Double.MAX_VALUE;
                }
            }

        }
        return ro_ln_ro;
    }

    private static double[][] gray_scale_image(MyColor[][] image) {
        int width = image[0].length;
        int height = image.length;
        double[][] grays = new double[height][width];

        for (int i=0;i<height;i++) {
            for (int j = 0; j < width; j++) {
                grays[i][j] = image[i][j].toGray();
            }
        }
        return grays;
    }

    public static double[][] createDynamicMap(double[][] energy) {


        int height = energy.length;
        int width = energy[0].length;
        double[][] result = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                double min = 1000000;//Double.MAX_VALUE;
                for (int width_diff = -1; width_diff <= 1; width_diff++) {
                    try {
                        double neighborResult = result[i -1][j+width_diff];
                        if (neighborResult < min) {
                            min = neighborResult;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
                if (min==1000000) { // Double.MAX_VALUE){
                    min = 0;
                }
                result[i][j] = min + energy[i][j];
            }

        }
        return result;
    }

    public static double[][] energy(MyColor[][] img, int energy_type) throws InputMismatchException {
        switch (energy_type){
            case (0):
                return createDynamicMap(energy0(img));
            case (1):
                return createDynamicMap(energy1(img));
            case (2):
                return createDynamicMap2(energy2(img));
            default:
                throw new InputMismatchException("no energy type");
        }
    }



    public static double[][][] energy2(MyColor[][] image) {
        //Forward Energy
        int width = image[0].length;
        int height = image.length;

        double[][][] cl_cu_cr = new double[height][width][3];


        double[][] grays = gray_scale_image(image);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // todo- borders
                int up = i==0 ? 0 : i-1; //i-1
                int right = j == width-1 ? j : j+1; //j+1
                int left = j==0 ? 0 : j-1;//j-1



                cl_cu_cr[i][j][0] = Math.abs(grays[i][right] - grays[i][left]) + Math.abs(grays[up][j] - grays[i][left]);
                cl_cu_cr[i][j][1] = Math.abs(grays[i][right] - grays[i][left]);
                cl_cu_cr[i][j][2] = Math.abs(grays[i][right] - grays[i][left]) + Math.abs(grays[up][j] - grays[i][right]);

            }
        }
        return cl_cu_cr;
    }

    public static double[][] createDynamicMap2(double[][][] cl_cu_cr){
        int width = cl_cu_cr[0].length;
        int height = cl_cu_cr.length;
        double[][] result = new double[height][width];


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                double min = Double.MAX_VALUE;
                double val;

                for (int width_diff = -1; width_diff<=1; width_diff++){
                    try {
                        val = result[i-1][j + width_diff] + cl_cu_cr[i][j][width_diff + 1];
                        if (val < min) {
                            min = val;
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        continue;
                    }
                }
                if (min == Double.MAX_VALUE){
                    min = 0;
                }
                result[i][j] = min;

            }

        }
        return result;

    }
}

