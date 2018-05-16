package com.sudo.v3.spectre.statics;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.script.framework.logger.BotLogger;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.statics.enums.DurationStringFormat;

/**
 * UpdateUI class for debugging purposes
 */
public class UpdateUI
{
    public static void debug(String debug){
        System.out.println(debug);
    }

    public static void debugAndLog(String debug){
        //debug(debug);
        Environment.getLogger().println(BotLogger.Level.DEBUG, debug);
    }

    public static void currentTask(String task, SudoBot bot){
        bot.setCurrentTask(task);
        debugAndLog(task);
    }

    public static void antiBanTask(String task, SudoBot bot){
        bot.setAntiBanTask(task);
        debugAndLog(task);
    }

    public static String getDurationString(long seconds, DurationStringFormat stringFormat) {

        long days = seconds / 86400;
        long hours = (seconds / 3600) % 24;
        long minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        if (stringFormat == DurationStringFormat.CLOCK) {
            if (days > 0)
                return twoDigitString(days) + ":" + twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
            else
                return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
        } else {
            if (days > 0)
                return twoDigitString(days) + "Day(s) " + twoDigitString(hours) + "Hour(s) " + twoDigitString(minutes) + "Min(s) " + twoDigitString(seconds) + "Sec(s)";
            else
                return twoDigitString(hours) + "Hour(s) " + twoDigitString(minutes) + "Min(s) " + twoDigitString(seconds) + "Sec(s)";
        }
    }

    private static String twoDigitString(long number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }
}
