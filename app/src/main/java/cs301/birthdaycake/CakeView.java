package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class CakeView extends SurfaceView {

    private CakeModel cakeModel;


    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint greenCheckerBoardPaint = new Paint();
    Paint redCheckerBoardPaint = new Paint();
    Paint balloonColor = new Paint();
    Paint balloonStringColor = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 100.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;



    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cakeModel = new CakeModel();

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(Color.BLUE);  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        greenCheckerBoardPaint.setColor(0xFF3EE785); //bright green
        greenCheckerBoardPaint.setStyle(Paint.Style.FILL);
        redCheckerBoardPaint.setColor(0xFFF50235); //red
        redCheckerBoardPaint.setStyle(Paint.Style.FILL);
        balloonColor.setColor(0xFF0000FF);
        balloonColor.setStyle(Paint.Style.FILL);
        balloonStringColor.setColor(Color.GRAY);
        balloonStringColor.setStyle(Paint.Style.FILL);

        setBackgroundColor(Color.WHITE);  //better than black default

    }

    public CakeModel getCakeModel() {
        return cakeModel;
    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {

        if (cakeModel.hasCandles && cakeModel.numCandles != 0) {

            canvas.drawRect(left, bottom - candleHeight, left + candleWidth, bottom, candlePaint);

            if (cakeModel.candlesLit) {
                //draw the outer flame
                float flameCenterX = left + candleWidth / 2;
                float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
                canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

                //draw the inner flame
                flameCenterY += outerFlameRadius / 3;
                canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);
            }
            //draw the wick
            float wickLeft = left + candleWidth / 2 - wickWidth / 2;
            float wickTop = bottom - wickHeight - candleHeight;
            canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);
        }
    }

    public void drawCheckerBoard(Canvas canvas, float x, float y) {

        canvas.drawRect(cakeModel.newX - 100.0f,  cakeModel.newY - 100.0f, cakeModel.newX, cakeModel.newY, greenCheckerBoardPaint);
        canvas.drawRect(cakeModel.newX, cakeModel.newY - 100.0f, cakeModel.newX + 100.0f, cakeModel.newY, redCheckerBoardPaint);
        canvas.drawRect(cakeModel.newX - 100.0f, cakeModel.newY, cakeModel.newX, cakeModel.newY + 100.0f, redCheckerBoardPaint);
        canvas.drawRect(cakeModel.newX, cakeModel.newY, cakeModel.newX + 100.0f, cakeModel.newY + 100.0f, greenCheckerBoardPaint);

    }


    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        if(cakeModel.giveCoord==true)
        {
            String newX = String.valueOf(cakeModel.x);
            String newY = String.valueOf(cakeModel.y);
            Paint newColor = new Paint();
            newColor.setTextSize(30.0f);
            newColor.setColor(Color.RED);
            canvas.drawText(newX+", "+newY,1800.0f,700.0f,newColor);

            invalidate();
        }

        if (cakeModel.numCandles == 1) {
            drawCandle(canvas, cakeLeft + cakeWidth/2 - candleWidth/2, cakeTop);
            invalidate();
        } else if (cakeModel.numCandles == 2) {
            drawCandle(canvas, cakeLeft + cakeWidth/2 - candleWidth/2, cakeTop);
            drawCandle(canvas, cakeLeft + 2*cakeWidth/6 - candleWidth/2, cakeTop);
            invalidate();
        } else if (cakeModel.numCandles == 3) {
            drawCandle(canvas, cakeLeft + cakeWidth/2 - candleWidth/2, cakeTop);
            drawCandle(canvas, cakeLeft + 2*cakeWidth/6 - candleWidth/2, cakeTop);
            drawCandle(canvas, cakeLeft + 4*cakeWidth/6 - candleWidth/2, cakeTop);
            invalidate();
        } else if (cakeModel.numCandles == 4) {
            drawCandle(canvas, cakeLeft + cakeWidth/2 - candleWidth/2, cakeTop);
            drawCandle(canvas, cakeLeft + 2*cakeWidth/6 - candleWidth/2, cakeTop);
            drawCandle(canvas, cakeLeft + 4*cakeWidth/6 - candleWidth/2, cakeTop);
            drawCandle(canvas, cakeLeft + 1*cakeWidth/6 - candleWidth/2, cakeTop);
            invalidate();
        } else if (cakeModel.numCandles == 5) {
            drawCandle(canvas, cakeLeft + cakeWidth/2 - candleWidth/2, cakeTop);
            drawCandle(canvas, cakeLeft + 2*cakeWidth/6 - candleWidth/2, cakeTop);
            drawCandle(canvas, cakeLeft + 4*cakeWidth/6 - candleWidth/2, cakeTop);
            drawCandle(canvas, cakeLeft + 1*cakeWidth/6 - candleWidth/2, cakeTop);
            drawCandle(canvas, cakeLeft + 5*cakeWidth/6 - candleWidth/2, cakeTop);
            invalidate();
        }



        if (cakeModel.isBalloon == true) {
            //draw a balloon
            canvas.drawRect(190.0f + cakeModel.personBAddedX - 200.0f, 300.0f + cakeModel.personBAddedY - 175.0f, 210.0f + cakeModel.personBAddedX - 200.0f, 450.0f + cakeModel.personBAddedY - 200.0f, balloonStringColor);
            canvas.drawOval(100.0f + cakeModel.personBAddedX - 200.0f, 50.0f + cakeModel.personBAddedY - 150.0f, 300.0f + cakeModel.personBAddedX - 200.0f, 300.0f + cakeModel.personBAddedY - 150.0f, balloonColor);
        }

        invalidate();

        if (cakeModel.hasCheckerBoard) {
            drawCheckerBoard(canvas, cakeModel.newX, cakeModel.newY);
        }

    }//onDraw

}//class CakeView

