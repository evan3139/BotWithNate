package com.sudo.v3.base;

import com.sudo.v3.interfaces.ISudoTask;

/**
 * Created by SudoPro on 12/16/2016.
 */
public abstract class SudoTask implements ISudoTask
{
    protected boolean active = true;

    @Override
    public boolean isActive(){
        return active;
    }

    @Override
    public void deactivate(){
        active = false;
    }
}
