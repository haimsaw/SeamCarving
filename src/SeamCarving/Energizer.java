package SeamCarving;


public class Energizer {

    public static double[][] energy(MyColor[][] image){
        int width = image.length;
        int height = image[0].length;
        double[][] energyMap = new double[width][height];

        for (int i=0;i<width;i++){
            for (int j=0;j<height;j++){
                int num_of_nebors = 0;
                double energy = 0;

                for (int height_diff = -1; height_diff<=1;height_diff++){
                    for (int width_diff = -1; width_diff<=1; width_diff++){
                        if (width_diff==0 && height_diff==0){
                            continue;
                        }
                        try {
                            energy += image[i+width_diff][j+height_diff].norma1(image[i][j]);
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

    public static double[][] createMap(double[][] energy) {

        int width = energy.length;
        int height = energy[0].length;
        double[][] result = new double[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                double min = 100;//Double.MAX_VALUE;
                for (int width_diff = -1; width_diff <= 1; width_diff++) {
                    try {
                        double neighborResult = result[j-1][i + width_diff];
                        if (neighborResult < min) {
                            min = neighborResult;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
                if (min==100) { // Double.MAX_VALUE){
                    min = 0;
                }
                result[i][j] = min + energy[i][j];
            }

        }
        return result;
    }

}
