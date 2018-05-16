package com.sudo.v3.antiban;

import com.runemate.game.api.hybrid.GameEvents;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.common.SudoTimer;
import com.sudo.v3.spectre.statics.UpdateUI;
import com.sudo.v3.spectre.statics.enums.DurationStringFormat;

import java.util.concurrent.TimeUnit;


/**
 * Created by SudoPro on 3/31/2017.
 */
public class BreakHandler {

    private SudoTimer breakTimer, botTimer;
    private SudoBot bot;

    // minBreakDuration = 15 minutes
    // maxBreakDuration = 120 minutes
    // minDurationBetweenBreak = 60 minutes
    // maxDurationBetweenBreak = 360 minutes

    // Below is the above minutes represented in milliseconds (multiplied by  60,000)
    private int minBreakDuration = 900000, maxBreakDuration = 7200000, minDurationBetweenBreak = 3600000, maxDurationBetweenBreak = 21600000;

    public BreakHandler(SudoBot bot) {
        this.bot = bot;
    }

    public void startTimer() {
        breakTimer = new SudoTimer(minBreakDuration, maxBreakDuration);
        botTimer = new SudoTimer(minDurationBetweenBreak, maxDurationBetweenBreak);
        botTimer.start();
    }

    public void execute() {
        if (bot.currentlyBreaking) {
            if (breakTimer.getRemainingTime() <= 0) {
                botTimer = new SudoTimer(minDurationBetweenBreak, maxDurationBetweenBreak);
                UpdateUI.currentTask("Breaking complete. Next break will occur once Runtime reaches: " + UpdateUI.getDurationString(bot.STOPWATCH.getRuntime(TimeUnit.SECONDS) + bot.breakHandler.getBotTimer().getRemainingTimeInSeconds(), DurationStringFormat.CLOCK), bot);
                GameEvents.Universal.LOBBY_HANDLER.enable();
                GameEvents.Universal.LOGIN_HANDLER.enable();
                GameEvents.Universal.INTERFACE_CLOSER.enable();
                botTimer.start();
                bot.currentlyBreaking = false;
            }
        } else {
            if (botTimer.getRemainingTime() <= 0) {
                breakTimer = new SudoTimer(minBreakDuration, maxBreakDuration);
                GameEvents.Universal.LOBBY_HANDLER.disable();
                GameEvents.Universal.LOGIN_HANDLER.disable();
                GameEvents.Universal.INTERFACE_CLOSER.disable();
                UpdateUI.currentTask("Break has been triggered. Bot now breaking for " + breakTimer.getRemainingTimeInMinutes() + " minutes.", bot);
                UpdateUI.currentTask("The bot will resume execution once Runtime reaches: " + UpdateUI.getDurationString(bot.STOPWATCH.getRuntime(TimeUnit.SECONDS) + breakTimer.getRemainingTimeInSeconds(), DurationStringFormat.CLOCK), bot);
                breakTimer.start();
                bot.currentlyBreaking = true;
            }
        }
    }

    public void setMaxBreakDuration(int max) {
        maxBreakDuration = max;
    }

    public void setMinBreakDuration(int min) {
        minBreakDuration = min;
    }

    public int getMaxBreakDuration() {
        return maxBreakDuration;
    }

    public int getMinBreakDuration() {
        return minBreakDuration;
    }

    public void setMaxDurationBetweenBreak(int max) {
        maxDurationBetweenBreak = max;
    }

    public void setMinDurationBetweenBreak(int min) {
        minDurationBetweenBreak = min;
    }

    public int getMaxDurationBetweenBreak() {
        return maxDurationBetweenBreak;
    }

    public int getMinDurationBetweenBreak() {
        return minDurationBetweenBreak;
    }

    public SudoTimer getBotTimer(){
        return botTimer;
    }

    public SudoTimer getBreakTimer(){
        return breakTimer;
    }
}