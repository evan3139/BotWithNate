package com.sudo.v3.antiban;

import com.sudo.v3.spectre.common.SudoTimer;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.interfaces.IAntiBan;
import com.sudo.v3.spectre.statics.UpdateUI;

import java.util.ArrayList;

/**
 * Created by SudoPro on 3/5/2016.
 */
public class AntiBanHandler {

    private SudoTimer abTimer;
    private SudoBot bot;
    private int minTime = 5, maxTime = 60;

    public AntiBanHandler(SudoBot bot){
        this.bot = bot;
    }

    public void startTimer(){
        abTimer = new SudoTimer(minTime, maxTime);
        abTimer.start();
    }

    protected void resetTimer(){
        abTimer.reset();
        UpdateUI.antiBanTask("Anti-Ban Timer reset. Will perform anti-ban again in " + (abTimer.getRemainingTime() / 1000) + " seconds", bot);
    }

    public void executeAntiBan(ArrayList<IAntiBan> antiBanList){
        if(abTimer.getRemainingTime() <= 0) {
            UpdateUI.debug("Executing antiban...");
            antiBanList.get((int) (Math.random() * antiBanList.size())).execute(bot);
            resetTimer();
        }
    }

    public void setMaxTime(int max){
        maxTime = max;
    }

    public void setMinTime(int min){
        minTime = min;
    }

    public int getMaxTime(){
        return maxTime;
    }

    public int getMinTime(){
        return minTime;
    }
}
