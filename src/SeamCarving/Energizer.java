package SeamCarving;


public class Energizer {

    public static double[][] energy(MyColor[][] image){
        int width = image.length;
        int height = image[0].length;
        double[][] energyMap = new double[height][width];

        for (int i=0;i<width;i++){
            for (int j=0;j<height;j++){
                int num_of_nebors = 0;
                double energy = 0;

                for (int height_diff = -1; height_diff<=1;height_diff+=2){
                    for (int width_diff = -1; width_diff<=1; width_diff+=2){

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

}
