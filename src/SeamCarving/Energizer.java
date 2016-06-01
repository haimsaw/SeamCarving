package SeamCarving;


public class Energizer {

    public static double[][] energy0(MyColor[][] image){
        int width = image.length;
        int height = image[0].length;
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
        int width = image.length;
        int height = image[0].length;
        double[][] energyMap = new double[height][width];

        double[][] energy0 = energy0(image);
        double[][] ro_ln_ro = roLnRo(gray_scale_image(image));


        return energyMap;
    }

    private static double[][] roLnRo(double[][] grays) {
        int width = grays.length;
        int height = grays[0].length;
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
            }

        }
        return ro_ln_ro;
    }

    private static double[][] gray_scale_image(MyColor[][] image) {
        int width = image.length;
        int height = image[0].length;
        double[][] grays = new double[height][width];

        for (int i=0;i<height;i++) {
            for (int j = 0; j < width; j++) {
                grays[i][j] = image[i][j].toGray();
            }
        }
        return grays;
    }

    public static double[][] createDynamicMap(double[][] energy) {

        int width = energy.length;
        int height = energy[0].length;
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

}

