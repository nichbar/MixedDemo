package work.nich.mixeddemo.dagger2.entities;

import javax.inject.Inject;

/**
 * Created by nich on 2017/1/25.
 * Shoes entity.
 */

public class Shoe {

    @Inject
    public Shoe(){

    }

    @Override
    public String toString() {
        return "鞋子";
    }
}
