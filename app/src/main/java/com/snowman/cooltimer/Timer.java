package com.snowman.cooltimer;

public class Timer {
    private String minutes;
    private String seconds;
    private int milliseconds;

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMinutes(String minutes) {
        if(Integer.valueOf(minutes) < 10){
            minutes = "0"+ minutes;
        }
        this.minutes = minutes;
    }

    public void setSeconds(String seconds) {
        if(Integer.valueOf(seconds) < 10){
            seconds = "0"+ seconds;
        }
        this.seconds = seconds;
    }

    public void setDefaultTime(){
        this.minutes = "00";
        this.seconds = "30";
        this.milliseconds = 30000;

    }
    public String getTime(){
        return minutes + ":" + seconds;
    }
    public void setTime(int milliseconds){
        setMinutes(String.valueOf(milliseconds/1000/60));
        setSeconds(String.valueOf(milliseconds/1000%60));
        this.milliseconds = milliseconds;
    }

}
