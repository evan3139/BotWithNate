package com.sudo.v3.spectre.common.navigation;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.Web;

import java.util.ArrayList;

/**
 * Created by SudoPro on 11/23/2016.
 */
public class Navigator
{
    public Web web;
    public ArrayList<Path> paths;

    public Navigator()
    {
        web = Traversal.getDefaultWeb();
        paths = new ArrayList<>();
    }

    public Navigator(Coordinate from, Coordinate toCoord)
    {
        web = Traversal.getDefaultWeb();
        paths.add(web.getPathBuilder().buildTo(toCoord));
    }

    public Navigator(Coordinate from, Area toArea)
    {
        web = Traversal.getDefaultWeb();
        paths.add(web.getPathBuilder().buildTo(toArea.getRandomCoordinate()));
    }

    // Add vertices to the web
    public void addToWeb()
    {

    }
}
