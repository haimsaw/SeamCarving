package SeamCarving;


public class Energizer {

    public static double[][] energy(MyColor[][] image){
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

}

