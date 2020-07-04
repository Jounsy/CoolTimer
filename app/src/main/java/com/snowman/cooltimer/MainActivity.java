package com.snowman.cooltimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    SeekBar seekBar;
    TextView textView;
    boolean isTimerOn;
    Timer timer;
    CountDownTimer countDownTimer;
    Ringtone defaultRingtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    textView = findViewById(R.id.textView);
    seekBar = findViewById(R.id.seekBar);
    timer = new Timer();
    seekBar.setOnSeekBarChangeListener(this);
    defaultRingtone = RingtoneManager.getRingtone(this,Settings.System.DEFAULT_RINGTONE_URI);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickButton(View view) {

    Button button = (Button)view;

    if (isTimerOn){
        isTimerOn = false;
        timer.setDefaultTime();
        seekBar.setEnabled(true);
        seekBar.setProgress(timer.getMilliseconds(),true);
        button.setText("Start");
        textView.setText(timer.getTime());
        countDownTimer.cancel();
        defaultRingtone.stop();
        Toast.makeText(this, "Таймер остановлен", Toast.LENGTH_SHORT).show();
    }
    else{
        isTimerOn = true;
        seekBar.setEnabled(false);
        button.setText("Stop");
        startTimer(timer.getMilliseconds());
        Toast.makeText(this, "Таймер запущен", Toast.LENGTH_SHORT).show();
    }


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        timer.setTime(seekBar.getProgress());
        textView.setText(timer.getTime());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void startTimer(final int milliseconds){
     countDownTimer = new CountDownTimer(milliseconds, 1000) {

            public void onTick(long millisUntilFinished) {
               timer.setTime((int) millisUntilFinished);
               textView.setText(timer.getTime());
            }

            public void onFinish() {
                defaultRingtone.play();
                Toast.makeText(MainActivity.this, "Запуск звукового оповещения!", Toast.LENGTH_SHORT).show();
            }

        }.start();


    }
}