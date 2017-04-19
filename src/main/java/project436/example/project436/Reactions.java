package project436.example.project436;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class Reactions extends AppCompatActivity {

    int counter = 0;
    ImageButton button;
    Random rand;

    double tempHeight = Resources.getSystem().getDisplayMetrics().heightPixels / 1.5;
    double tempWidth = Resources.getSystem().getDisplayMetrics().widthPixels / 1.5;
    final int height = (int) tempHeight;
    final int width = (int) tempWidth;

    double time;
    double avgWidthTraveled;
    double avgHeightTraveled;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reactions);

        button = (ImageButton) findViewById(R.id.button);
        time = (double) System.currentTimeMillis();
        balloonCheck();

        Toast.makeText(Reactions.this, "Start!", Toast.LENGTH_SHORT).show();
    }

    public void balloonCheck() {
        rand = new Random();
        //to implement: selective randomness, create circle and choose along it
        int tempRandWidth = rand.nextInt(width);
        int tempRandHeight = rand.nextInt(height);
        avgHeightTraveled += (double) tempRandHeight;
        avgWidthTraveled += (double) tempRandWidth;
        button.setX(tempRandWidth);
        button.setY(tempRandHeight);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                counter++;
                if (counter < 10) {
                    //Toast.makeText(Reactions.this, "Current: " + counter, Toast.LENGTH_SHORT).show();
                    balloonCheck();
                } else if (counter == 10) {
                    double totalTime = ((double)System.currentTimeMillis()- time)/1000.0;
                    float totalTimeFloat = (float) totalTime;

                    avgHeightTraveled /= 10.0;
                    avgWidthTraveled /= 10.0;

                    Toast.makeText(Reactions.this, "Done: " + totalTime + " seconds" + "\n" + "AvgHorizontalMove: " + avgWidthTraveled + "\n" + "AvgVerticalMove: " + avgHeightTraveled, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Reactions.this, "Test complete. Return to main menu to try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

}
