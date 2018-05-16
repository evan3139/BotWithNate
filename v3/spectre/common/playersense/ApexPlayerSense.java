package com.sudo.v3.spectre.common.playersense;

import com.runemate.game.api.hybrid.player_sense.PlayerSense;
import com.runemate.game.api.hybrid.util.calculations.Random;

import java.util.function.Supplier;


/**
 * Created by Proxify on 9/24/2017.
 */
public class ApexPlayerSense {
    public static void initializeKeys() {
        for (Key key : Key.values()) {
            if (PlayerSense.get(key.name) == null) {
                PlayerSense.put(key.name, key.supplier.get());
            }
        }
    }

    public enum Key {
        // APEX
        TRAVERSAL_DELAY("apex_traversal_delay", () -> Random.nextInt(500, 1500)),
        HOVER_OVER_NEXT_PICKUP("apex_hover_over_next_pickup", () -> Random.nextBoolean()),
        HOVER_OVER_DEPOSIT_LOW_INVENTORY_COUNT("apex_hover_over_deposit_low_inventory_count", () -> Random.nextInt(4, 12)),
        HOVER_OVER_DEPOSIT_HIGH_INVENTORY_COUNT("apex_hover_over_deposit_high_inventory_count", () -> Random.nextInt(19, 26)),

        // REGAL
        FAST_CLICK_PATTERN("regal_fast_click_pattern", ()->Random.nextInt(0, 5)),
        FAST_CLICK_PRIMARY_WEIGHTING("regal_fast_click_primary", ()->Random.nextDouble(80, 92)),
        FAST_CLICK_SECOND_WEIGHTING("regal_fast_click_primary", ()->Random.nextDouble(30, 50)),
        FAST_CLICK_THIRD_WEIGHTING("regal_fast_click_primary", ()->Random.nextDouble(20, 50)),
        CHANCE_OF_RANDOM_PATTERN("regal_chance_of_random_pattern", ()->Random.nextDouble(.05, .18)),
        REACTION_TIME("regal_reaction_time", ()->Random.nextDouble(0.3, 2));

        private final String name;
        private final Supplier supplier;

        Key(String name, Supplier supplier) {
            this.name = name;
            this.supplier = supplier;
        }

        public String getKey() {
            return name;
        }

        public Integer getAsInteger() {
            return PlayerSense.getAsInteger(name);
        }

        public Double getAsDouble() {
            return PlayerSense.getAsDouble(name);
        }

        public Long getAsLong() {
            return PlayerSense.getAsLong(name);
        }

        public Boolean getAsBoolean() {
            return PlayerSense.getAsBoolean(name);
        }
    }
}