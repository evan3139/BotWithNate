package com.sudo.v3.spectre.statics;

import java.util.HashSet;

/**
 * Created by Proxify on 9/12/2017.
 */
public class UserList {
    public static HashSet<String> blacklist = new HashSet<String>(){{
    }};

    public static HashSet<String> whiteList = new HashSet<String>(){{
        add("Snufalufugus");
    }};
}
