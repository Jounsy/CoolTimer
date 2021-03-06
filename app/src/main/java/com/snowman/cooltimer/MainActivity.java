package com.snowman.cooltimer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
               if(sharedPreferences.getBoolean("enable_sound", true)) {
                   defaultRingtone.play();
               }
                Toast.makeText(MainActivity.this, "Запуск звукового оповещения!", Toast.LENGTH_SHORT).show();
            }

        }.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings){
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);
            return true;
        } else if (id==R.id.action_about){
            Intent openAbout = new Intent(this, AboutActivity.class);
            startActivity(openAbout);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}