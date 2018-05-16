package com.sudo.v3.spectre.bots.apexcrabber.threads;


import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.statics.UpdateUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * Created by joeyw on 4/21/2017.
 */
public class TrialWorker extends Thread {

    public SudoBot bot;
    private String key = "Trial Runtime";

    public TrialWorker(SudoBot bot){
        this.bot = bot;

    }

    @Override
    public void run(){
        String localDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String value;

        // If the key exists
        if (bot.managedProperties.containsKey(key)) {
            value = bot.managedProperties.getProperty(key);
            UpdateUI.debug("Key found, value is: " + value);
        }
        else {
            UpdateUI.debug("Key not found");
            value = null;
        }

        if (value != null) {
            // Using trialPattern initialized on startup

            // Grab Matches from our current value
            Matcher matcher = bot.trialPattern.matcher(value);

            // If any matches exist
            if (matcher.find()) {

                // If the Trial Date is the same as the Local Date, then the botter is resuming a "trial" session
                if (matcher.group(1).equals(localDate)) {
                    bot.trialRunTime = Long.valueOf(matcher.group(2)) + bot.STOPWATCH.getRuntime(TimeUnit.MILLISECONDS);
                } else {
                    // If the Trial Date does Not equal the Local Date, then it is a new day and the botter can use this bot for an hour
                    bot.trialRunTime = bot.STOPWATCH.getRuntime();
                }
            }
        }

        bot.managedProperties.setProperty(key, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + " - " + bot.trialRunTime);

    }
}
