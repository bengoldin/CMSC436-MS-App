package project436.example.project436;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

//import edu.umd.cmsc436.sheets.Sheets;

public class StepSpeed extends AppCompatActivity implements SensorEventListener, StepListener {

    /*
    public static final int LIB_ACCOUNT_NAME_REQUEST_CODE = 1001;
    public static final int LIB_AUTHORIZATION_REQUEST_CODE = 1002;
    public static final int LIB_PERMISSION_REQUEST_CODE = 1003;
    public static final int LIB_PLAY_SERVICES_REQUEST_CODE = 1004;
    public static final int LIB_CONNECTION_REQUEST_CODE = 1005;

    private Sheets sheet;
    */

    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;

    private TextView TvSteps;
    private Button BtnStart;
    private Button BtnStop;

    double time;
    double avgInternalVelocity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_speed);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TvSteps = (TextView) findViewById(R.id.tv_steps);
        BtnStart = (Button) findViewById(R.id.btn_start);
        BtnStop = (Button) findViewById(R.id.btn_stop);

        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                numSteps = 0;
                sensorManager.registerListener(StepSpeed.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sensorManager.unregisterListener(StepSpeed.this);
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        DecimalFormat df = new DecimalFormat("#.###");

        if (numSteps == 0) {
            time = (double) System.currentTimeMillis();
            numSteps++;
            double vel = (double) simpleStepDetector.returnVel();
            avgInternalVelocity += vel;
            TvSteps.setText(TEXT_NUM_STEPS + numSteps + " " + df.format(vel));
        } else if (numSteps == 25) {
            double totalTime = ((double)System.currentTimeMillis()- time)/1000.0;
            avgInternalVelocity /= 25.000;
            //25 steps * 22 inches per step = 550
            //inches/second * .0568182 = miles/hour

            double avgCalculatedVelocity = (550.000/totalTime)*.0568182;

            TvSteps.setText(TEXT_NUM_STEPS + numSteps + " with avg velocity of " + df.format(avgInternalVelocity)
                    + "\n" + " step velocity of " + df.format(avgCalculatedVelocity));

            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);

            float avgCVfloat = (float) avgCalculatedVelocity;

            //sheet = new Sheets(StepSpeed.this, StepSpeed.this, getString(R.string.app_name), getString(R.string.CMSC436_my_testing_spreadsheet), getString(R.string.CMSC436_my_private_test_spreadsheet));
            //sheet.writeData(Sheets.TestType.INDOOR_WALKING, getString(R.string.user_id), avgCVfloat);
            //float[] trialData = {avgCVfloat, avgCVfloat, avgCVfloat};
            //sheet.writeTrials(Sheets.TestType.INDOOR_WALKING, getString(R.string.user_id), trialData);

            numSteps++;
            sensorManager.unregisterListener(StepSpeed.this);
        } else {
            numSteps++;
            double vel = (double) simpleStepDetector.returnVel();
            TvSteps.setText(TEXT_NUM_STEPS + numSteps + " " + df.format(vel));
        }
    }

    /*
    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        sheet.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sheet.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int getRequestCode(Sheets.Action action) {
        switch (action) {
            case REQUEST_ACCOUNT_NAME:
                return LIB_ACCOUNT_NAME_REQUEST_CODE;
            case REQUEST_AUTHORIZATION:
                return LIB_AUTHORIZATION_REQUEST_CODE;
            case REQUEST_PERMISSIONS:
                return LIB_PERMISSION_REQUEST_CODE;
            case REQUEST_PLAY_SERVICES:
                return LIB_PLAY_SERVICES_REQUEST_CODE;
            case REQUEST_CONNECTION_RESOLUTION:
                return LIB_CONNECTION_REQUEST_CODE;
            default:
                return -1;
        }
    }

    @Override
    public void notifyFinished(Exception e) {
        if (e != null) {
            throw new RuntimeException(e);
        }
        Log.i(getClass().getSimpleName(), "Done");
    }
*/

}
