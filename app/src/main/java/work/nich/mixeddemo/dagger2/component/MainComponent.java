package work.nich.mixeddemo.dagger2.component;

import dagger.Component;
import work.nich.mixeddemo.activities.Dagger2Activity;
import work.nich.mixeddemo.dagger2.modules.MainModule;

/**
 * Created by nich on 2017/1/25.
 * MainComponent.
 */

@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(Dagger2Activity dagger2Activity);
}
