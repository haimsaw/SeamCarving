package SeamCarving;


import sun.security.mscapi.KeyStore;

import static java.lang.Math.abs;

public class MyColor {
    public float r;
    public float g;
    public float b;

    public MyColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float norma1(MyColor other){
        return (abs(this.r - other.r) +abs(this.g - other.g) +abs(this.b - other.b))/3;
    }
}
