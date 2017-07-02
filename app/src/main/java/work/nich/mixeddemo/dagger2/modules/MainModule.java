package work.nich.mixeddemo.dagger2.modules;

import dagger.Module;
import dagger.Provides;
import work.nich.mixeddemo.dagger2.entities.Cloth;
import work.nich.mixeddemo.dagger2.entities.Clothes;

/**
 * Created by nich on 2017/1/25.
 * Module.
 */

@Module
public class MainModule {

    @Provides
    public Cloth getCloth() {
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return cloth;
    }

    @Provides
    public Clothes getClothes(Cloth cloth){
        return new Clothes(cloth);
    }
}