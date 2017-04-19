package project436.example.project436;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.util.Date;


public class Clicker extends AppCompatActivity {

    TextView tvTime;
    Button bClick, bStart;

    CountDownTimer timer;
    int time = 10;

    int clicks = 0;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = (TextView) findViewById(R.id.tvTime);
        bClick = (Button) findViewById(R.id.bClick);
        bStart = (Button) findViewById(R.id.bStart);

        bStart.setEnabled(true);
        bClick.setEnabled(false);

        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time--;
                tvTime.setText("Time: " + time);
            }

            @Override
            public void onFinish() {
                time--;
                tvTime.setText("Time: " + time);
                bStart.setEnabled(true);
                bClick.setEnabled(false);

                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String printedString = (String.valueOf(clicks) + " clicks on " + currentDateTimeString + ".");
                String temp="";


                AlertDialog alertDialog = new AlertDialog.Builder(Clicker.this).create();
                alertDialog.setTitle("You got: ");
                alertDialog.setMessage(printedString);
                alertDialog.setCancelable(true);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        };

        bClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks++;
            }
        });

        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                bStart.setEnabled(false);
                bClick.setEnabled(true);
                clicks = 0;
                time = 10;
                tvTime.setText("Time: " + time);
            }
        });
    }


}
