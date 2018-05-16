package com.sudo.v3.spectre.common;

import com.runemate.game.api.hybrid.entities.details.Interactable;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.local.Camera;
import com.sudo.v3.spectre.statics.UpdateUI;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Proxify on 8/19/2017.
 */
public class SudoCamera {
    private static final double lowYaw = 0.4, highYaw = 0.6;

    public static <T extends Interactable & Locatable> Future<Boolean> ConcurrentlyTurnToWithYaw(T object) {
        double randomDouble = ThreadLocalRandom.current().nextDouble(lowYaw, highYaw);
        UpdateUI.debug("Rotating camera to entity with yaw: " + randomDouble);

        return Camera.concurrentlyTurnTo(object, randomDouble);
    }
}
