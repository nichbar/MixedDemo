package work.nich.mixeddemo.dagger2.entities;

/**
 * Created by nich on 2017/1/25.
 * Cloth.
 */

public class Cloth {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color + "布料";
    }
}