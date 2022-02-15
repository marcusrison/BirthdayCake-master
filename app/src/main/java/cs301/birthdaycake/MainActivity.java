package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        CakeView viewOfCake = (CakeView) findViewById(R.id.cakeview);
        CakeController controller = new CakeController(viewOfCake);

        Button blowOut = (Button) findViewById(R.id.blowOutButton);
        blowOut.setOnClickListener(controller);

        Switch wantCandlesButton = (Switch) findViewById(R.id.CandlesSwitchButton);
        wantCandlesButton.setOnCheckedChangeListener(controller);

        SeekBar candleSeekbar = (SeekBar) findViewById(R.id.candleSeekBar);
        candleSeekbar.setOnSeekBarChangeListener(controller);

        viewOfCake.setOnTouchListener(controller);
    }

    public void goodbye(View button) {
        Log.i("button", "Goodbye");
    }

}
