package com.sudo.v3.spectre.common;

import com.runemate.game.api.hybrid.util.Timer;

/**
 * SudoTimer
 *
 * Wrapper class for RuneMate's Timer Class
 */
public class SudoTimer
{

    private Timer timer;
    private int minTime, maxTime;

    public SudoTimer(int min, int max)
    {
        minTime = min;
        maxTime = max;
        timer = new Timer(min + (int) (Math.random() * (max - min)));
    }

    public long getRemainingTime()
    {
        return timer.getRemainingTime();
    }

    public long getRemainingTimeInSeconds()
    {
        return timer.getRemainingTime() / 1000;
    }

    public long getRemainingTimeInMinutes(){
        return timer.getRemainingTime() / 60000;
    }

    public boolean hasExpired(){
        return timer.getRemainingTime() <= 0;
    }

    public void setRemainingTime(int time)
    {
        timer = new Timer(time);
    }

    public void reset()
    {
        timer.stop();
        timer = new Timer(minTime + (int) (Math.random() * (maxTime - minTime)));
        timer.start();
    }

    public void restartSameTimer()
    {
        timer.reset();
    }

    public void start()
    {
        timer.start();
    }

    public void stop()
    {
        timer.stop();
    }
}