package cs301.birthdaycake;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    private CakeView cakeview;
    private CakeModel cakeModel;

    public CakeController(CakeView initCakeView) {
        cakeview = initCakeView;
        cakeModel  = cakeview.getCakeModel();
    }

    @Override
    public void onClick(View view) {
        Log.d("blow out button","Clicking");
        cakeModel.candlesLit = false;
        cakeview.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        cakeModel.hasCandles = b;
        cakeview.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        cakeModel.numCandles = i;
        cakeview.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Do nothing
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //Do nothing
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        cakeModel.giveCoord = true;
        cakeModel.x = x;
        cakeModel.y = y;
        cakeview.invalidate();
        return true;
    }
}
