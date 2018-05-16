package com.sudo.v3.spectre.common.playersense;

import com.runemate.game.api.script.Execution;

import java.util.concurrent.Callable;

public class DelaysPsensed {
    private static double multiplier = ApexPlayerSense.Key.REACTION_TIME.getAsDouble();
    public static void delay(int min, int max, int avg){
        Execution.delay((int)(min*multiplier), (int)(max*multiplier), (int)(avg*multiplier));
    }
    public static boolean delayUntil(Callable<Boolean> toRun, Callable<Boolean>  reset, int minTimeout, int maxTimeout) {
        return Execution.delayUntil(toRun, reset, (int) (multiplier * minTimeout), (int) (multiplier*maxTimeout));
    }
    public static boolean delayUntil(Callable<Boolean> toRun, Callable<Boolean>  reset, int maxTimeout) {
        return Execution.delayUntil(toRun, reset, (int) (multiplier*maxTimeout));
    }
    public static boolean delayUntil(Callable<Boolean> until, int minTimeout, int maxTimeout) {
        return Execution.delayUntil(until, (int) (multiplier * minTimeout), (int) (multiplier * maxTimeout));
    }
    public static boolean delayUntil(Callable<Boolean> until, int maxTimeout) {
        return Execution.delayUntil(until, (int) (multiplier * maxTimeout));
    }
}