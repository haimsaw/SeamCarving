package SeamCarving;


public class Energizer {

    public static float[][] energy1(MyColor[][] image){
        int width = image.length;
        int height = image[0].length;
        float[][] energyMap = new float[height][width];

        for (int i=0;i<width;i++){
            for (int j=0;j<height;j++){
                int num_of_nebors = 0;
                float energy = 0;

                for (int height_diff = -1; height_diff<=1;height_diff++){
                    for (int width_diff = -1; width_diff<=1; width_diff++){
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
