package SeamCarving;
import static java.lang.Math.abs;

public class MyColor {
    public double r;
    public double g;
    public double b;

    public MyColor(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double norma1(MyColor other){
        return (abs(this.r - other.r) + abs(this.g - other.g) + abs(this.b - other.b))/3;
    }


    public String toString() {
        return String.format("%f, %f, %f", r, g, b);
    }

    public double toGray(){
        return (r+g+b)/3;
    }
}
