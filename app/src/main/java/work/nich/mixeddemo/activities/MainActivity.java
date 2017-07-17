package work.nich.mixeddemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import work.nich.chinesename.YourName;
import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSimpleWebView(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.setAction("file:///android_asset/webkitQ.html");
        startActivity(intent);
    }

    public void openMessenger(View view) {
        Intent intent = new Intent(this, MessengerActivity.class);
        startActivity(intent);
        String x;
    }

    public void openCustomView(View view) {
        Intent intent = new Intent(this, CustomViewActivity.class);
        startActivity(intent);
    }

    public void openCoordinator(View view) {
        Intent intent = new Intent(this, CoordinatorActivity.class);
        startActivity(intent);
    }

    public void openDagger2(View view) {
        Intent intent = new Intent(this, Dagger2Activity.class);
        startActivity(intent);
    }

    public void testYourName(View view) {
        YourName yourName = new YourName(this);
        Toast.makeText(this, yourName.generateName(YourName.TWO_CHARACTER), Toast.LENGTH_SHORT).show();
    }


    public void openPermission(View view) {
        Intent intent = new Intent(this, PermissionTrialActivity.class);
        startActivity(intent);
    }

    public void selectPic(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                null,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void openBinder(View view) {
        startActivity(new Intent(this, BinderActivity.class));
    }
}
