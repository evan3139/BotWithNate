package com.squidl.bots.HosidiusThieving;

import com.runemate.game.api.script.framework.task.TaskBot;
import com.squidl.bots.HosidiusThieving.tasks.Dropfruit;
import com.squidl.bots.HosidiusThieving.tasks.Eatfruit;
import com.squidl.bots.HosidiusThieving.tasks.WalkToStalls;

public class Thieving extends TaskBot {

    @Override
    public void onStart(String... args) {
        setLoopDelay(200, 400);
        add(new Eatfruit(), new Dropfruit(), new WalkToStalls());
    }
}
