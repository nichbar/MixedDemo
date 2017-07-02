package work.nich.mixeddemo.activities;

import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.dagger2.component.DaggerMainComponent;
import work.nich.mixeddemo.dagger2.component.MainComponent;
import work.nich.mixeddemo.dagger2.entities.Clothes;
import work.nich.mixeddemo.dagger2.entities.Shoe;
import work.nich.mixeddemo.dagger2.modules.MainModule;
import work.nich.mixeddemo.dagger2.entities.Cloth;

/**
 * Created by nich on 2017/1/25.
 * A place to learn dagger2.
 */

public class Dagger2Activity extends BaseActivity {
    @Bind(R.id.dagger2_text)
    TextView mainText;

    @Inject
    Cloth cloth;

    @Inject
    Shoe shoe;

    @Inject
    Clothes clothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
        ButterKnife.bind(this);

        MainComponent builder = DaggerMainComponent.builder().mainModule(new MainModule()).build();
        builder.inject(this);

        mainText.setText("我现在有" + cloth + "和" + shoe  + "和" + clothes);
    }
}
