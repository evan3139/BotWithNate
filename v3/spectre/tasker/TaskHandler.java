package com.sudo.v3.spectre.tasker;

import com.sudo.v3.base.SudoTask;

import java.util.ArrayList;

/**
 * Created by SudoPro on 12/16/2016.
 */
public class TaskHandler
{
    private ArrayList<SudoTask> tasks;

    public TaskHandler()
    {
        tasks = new ArrayList<>();
    }

    public TaskHandler(SudoTask task)
    {
        tasks = new ArrayList<>();
        tasks.add(task);
    }

    public void addTask(SudoTask task)
    {
        tasks.add(task);
    }

    public SudoTask getCurrentTask()
    {

        for (SudoTask task : tasks)
        {
            if (task.isActive())
                return task;
        }

        return null;
    }

    public void remove(SudoTask task){
        tasks.remove(task);
    }

    public void removeAll(){
        tasks = new ArrayList<>();
    }
}