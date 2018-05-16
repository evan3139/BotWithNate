package com.sudo.v3.interfaces;

/**
 * Created by SudoPro on 12/16/2016.
 */
public interface ISudoTask
{
    boolean isActive();
    boolean isValid();

    void deactivate();
}
