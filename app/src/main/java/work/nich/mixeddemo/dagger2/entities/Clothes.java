package work.nich.mixeddemo.dagger2.entities;

/**
 * Created by nich on 2017/1/25.
 */

public class Clothes {
    private Cloth cloth;

    public Clothes(Cloth cloth){
        this.cloth = cloth;
    }

    public Cloth getCloth(){
        return cloth;
    }

    @Override
    public String toString() {
        return cloth.getColor() + "衣服";
    }
}
