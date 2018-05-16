package com.sudo.v3.spectre.common.playersense.inventory;

import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.sudo.v3.spectre.common.playersense.ApexPlayerSense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum PatternType {

    COLUMN,
    COLUMN_V1,
    COLUMN_V2,
    COLUMN_V3,
    ROW,
    ROW_V1,
    ROW_V2,
    ROW_V3,
    SNAKE,
    SNAKE_V1,
    SNAKE_V2,
    SNAKE_V3,
    VERTICAL_SNAKE,
    VERTICAL_SNAKE_V1,
    VERTICAL_SNAKE_V2,
    VERTICAL_SNAKE_V3,
    HORIZONTAL_TWO_COLUMN_SNAKE,
    HORIZONTAL_TWO_COLUMN_SNAKE_V1,
    HORIZONTAL_TWO_COLUMN_SNAKE_V2,
    HORIZONTAL_TWO_COLUMN_SNAKE_V3;

    private final static int[] sortByColumn = new int[]{
            0, 4, 8, 12, 16, 20, 24,
            1, 5, 9, 13, 17, 21, 25,
            2, 6, 10, 14, 18, 22, 26,
            3, 7, 11, 15, 19, 23, 27};

    private final static int[] sortByColumnV1 = new int[]{ //columns from bottom up instead
            24, 20, 16, 12, 8, 4, 0,
            25, 21, 17, 13, 9, 5, 1,
            26, 22, 18, 14, 10, 6, 2,
            27, 23, 19, 15, 11, 7, 3};

    private final static int[] sortByColumnV2 = new int[]{ //columns from right to left instead
            3, 7, 11, 15, 19, 23, 27,
            2, 6, 10, 14, 18, 22, 26,
            1, 5, 9, 13, 17, 21, 25,
            0, 4, 8, 12, 16, 20, 24};

    private final static int[] sortByColumnV3 = new int[]{ //columns from bottom up, right to left
            27, 23, 19, 15, 11, 7, 3,
            26, 22, 18, 14, 10, 6, 2,
            25, 21, 17, 13, 9, 5, 1,
            24, 20, 16, 12, 8, 4, 0};

    private final static int[] sortByRow = new int[]{
            0, 1, 2, 3,
            4, 5, 6, 7,
            8, 9, 10, 11,
            12, 13, 14, 15,
            16, 17, 18, 19,
            20, 21, 22, 23,
            24, 25, 26, 27};

    private final static int[] sortByRowV1 = new int[]{ //rows from bottom up instead
            24, 25, 26, 27,
            20, 21, 22, 23,
            16, 17, 18, 19,
            12, 13, 14, 15,
            8, 9, 10, 11,
            4, 5, 6, 7,
            0, 1, 2, 3};

    private final static int[] sortByRowV2 = new int[]{ //rows from right to left instead
            3, 2, 1, 0,
            7, 6, 5, 4,
            11, 10, 9, 8,
            15, 14, 13, 12,
            19, 18, 17, 16,
            23, 22, 21, 20,
            27, 26, 25, 24};

    private final static int[] sortByRowV3 = new int[]{ //rows from bottom up, right to left
            27, 26, 25, 24,
            23, 22, 21, 20,
            19, 18, 17, 16,
            15, 14, 13, 12,
            11, 10, 9, 8,
            7, 6, 5, 4,
            3, 2, 1, 0};

    private final static int[] sortBySnake = new int[]{ //snakes from left to right, then right to left, etc
            0, 1, 2, 3,
            7, 6, 5, 4,
            8, 9, 10, 11,
            15, 14, 13, 12,
            16, 17, 18, 19,
            23, 22, 21, 20,
            24, 25, 26, 27};

    private final static int[] sortBySnakeV1 = new int[]{ //snakes from right to left, then left to right, etc
            3, 2, 1, 0,
            4, 5, 6, 7,
            11, 10, 9, 8,
            12, 13, 14, 15,
            19, 18, 17, 16,
            20, 21, 22, 23,
            27, 26, 25, 24};

    private final static int[] sortBySnakeV2 = new int[]{ //snakes from left to right, then right to left, etc, from bottom up
            24, 25, 26, 27,
            23, 22, 21, 20,
            16, 17, 18, 19,
            15, 14, 13, 12,
            8, 9, 10, 11,
            7, 6, 5, 4,
            0, 1, 2, 3};

    private final static int[] sortBySnakeV3 = new int[]{ //snakes from right to left, then left to right, etc, from bottom up
            27, 26, 25, 24,
            20, 21, 22, 23,
            19, 18, 17, 16,
            12, 13, 14, 15,
            11, 10, 9, 8,
            4, 5, 6, 7,
            3, 2, 1, 0};

    private final static int[] sortByVerticalSnake = new int[]{//from left to right
            0, 4, 8, 12, 16, 20, 24,
            25, 21, 17, 13, 9, 5, 1,
            2, 6, 10, 14, 18, 22, 26,
            27, 23, 19, 15, 11, 7, 3};

    private final static int[] sortByVerticalSnakeV1 = new int[]{//from right to left, bottom to top
            27, 23, 19, 15, 11, 7, 3,
            2, 6, 10, 14, 18, 22, 26,
            25, 21, 17, 13, 9, 5, 1,
            0, 4, 8, 12, 16, 20, 24};

    private final static int[] sortByVerticalSnakeV2 = new int[]{//from right to left, top to bottom
            3, 7, 11, 15, 19, 23, 27,
            26, 22, 18, 14, 10, 6, 2,
            1, 5, 9, 13, 17, 21, 25,
            24, 20, 16, 12, 8, 4, 0};

    private final static int[] sortByVerticalSnakeV3 = new int[]{//from left to right, bottom to top
            24, 20, 16, 12, 8, 4, 0,
            1, 5, 9, 13, 17, 21, 25,
            26, 22, 18, 14, 10, 6, 2,
            3, 7, 11, 15, 19, 23, 27};

    private final static int[] sortByHorizontalTwoColumnSnake = new int[]{
            0, 1, 5, 4, 8, 9, 13, 12, 16, 17, 21, 20, 24, 25, 26, 27, 23, 22, 18, 19, 15, 14, 10, 11, 7, 6, 2, 3};

    private final static int[] sortByHorizontalTwoColumnSnakeV1 = new int[]{
            0, 1, 5, 4, 8, 9, 13, 12, 16, 17, 21, 20, 24, 25, 26, 27, 22, 23, 19, 18, 14, 15, 11, 10, 6, 7, 3, 2};
    //the second half in the opposite snake trialPattern

    private final static int[] sortByHorizontalTwoColumnSnakeV2 = new int[]{
            1, 0, 4, 5, 9, 8, 12, 13, 17, 16, 20, 21, 24, 25, 26, 27, 22, 23, 19, 18, 14, 15, 11, 10, 6, 7, 3, 2};
    //the first half in the opposite snake trialPattern

    private final static int[] sortByHorizontalTwoColumnSnakeV3 = new int[]{
            1, 0, 4, 5, 9, 8, 12, 13, 17, 16, 20, 24, 25, 21, 22, 26, 27, 23, 19, 18, 14, 15, 11, 10, 6, 7, 3, 2};

    public static PatternType getRandomPattern(){
        ArrayList<PatternType> allPatterns = new ArrayList<>();
        allPatterns.addAll(Arrays.asList(PatternType.values()));
        int toGet = Random.nextInt(0, 20);
        if(allPatterns.size() >= toGet){
            return allPatterns.get(toGet);
        } else if (!allPatterns.isEmpty()){
            return allPatterns.get(0);
        } else {
            return null;
        }
    }

    public static PatternType getPlayerSensedPattern() {
        int psense = ApexPlayerSense.Key.FAST_CLICK_PATTERN.getAsInteger();
        double firstWeight = ApexPlayerSense.Key.FAST_CLICK_PRIMARY_WEIGHTING.getAsDouble();
        double secondWeight = ApexPlayerSense.Key.FAST_CLICK_SECOND_WEIGHTING.getAsDouble();
        double thirdWeight = ApexPlayerSense.Key.FAST_CLICK_THIRD_WEIGHTING.getAsDouble();
        if(psense == 0){
            if(Math.random() < firstWeight){
                return COLUMN;
            } else if (Math.random() < secondWeight){
                return COLUMN_V1;
            } else if (Math.random() < thirdWeight){
                return COLUMN_V2;
            } else {
                return COLUMN_V3;
            }
        } else if (psense == 1){
            if(Math.random() < firstWeight){
                return ROW;
            } else if (Math.random() < secondWeight){
                return ROW_V1;
            } else if (Math.random() < thirdWeight){
                return ROW_V2;
            } else {
                return ROW_V3;
            }
        } else if (psense == 2){
            if(Math.random() < firstWeight){
                return SNAKE;
            } else if (Math.random() < secondWeight){
                return SNAKE_V1;
            } else if (Math.random() < thirdWeight){
                return SNAKE_V2;
            } else {
                return SNAKE_V3;
            }
        } else if (psense == 3){
            if(Math.random() < firstWeight){
                return VERTICAL_SNAKE;
            } else if (Math.random() < secondWeight){
                return VERTICAL_SNAKE_V1;
            } else if (Math.random() < thirdWeight){
                return VERTICAL_SNAKE_V2;
            } else {
                return VERTICAL_SNAKE_V3;
            }
        } else if (psense == 4){
            if(Math.random() < firstWeight){
                return HORIZONTAL_TWO_COLUMN_SNAKE;
            } else if (Math.random() < secondWeight){
                return HORIZONTAL_TWO_COLUMN_SNAKE_V1;
            } else if (Math.random() < thirdWeight){
                return HORIZONTAL_TWO_COLUMN_SNAKE_V2;
            } else {
                return HORIZONTAL_TWO_COLUMN_SNAKE_V3;
            }
        } else { //backup - should never go here
            if(Math.random() < firstWeight){
                return COLUMN;
            } else if (Math.random() < secondWeight){
                return COLUMN_V1;
            } else if (Math.random() < thirdWeight){
                return COLUMN_V2;
            } else {
                return COLUMN_V3;
            }
        }
    }

    public static List<SpriteItem> patternizeItemList(List<SpriteItem> items, PatternType patternType) {
        // Create empty HashMap
        HashMap<Integer, SpriteItem> patternMap = new HashMap<>();

        //create int array to save chosen sorting method to
        int[] chosen;

        // From the items currently in inventory (already in sequential order), add them to the HashMap with their corresponding slot number (0 - 27)
        for (int i = 0; i < items.size(); i++) {
            patternMap.put(items.get(i).getIndex(), items.get(i));
        }

        // Create empty List to hold the new, sorted list
        List<SpriteItem> itemsSorted = new ArrayList<>();

        // Handle sorting by column
        if (patternType == PatternType.COLUMN) {
            // For each slot number we desire to prioritize, check if there is a sprite item in the slot, if so, add it to our sorted list
            chosen = sortByColumn;
        } else if (patternType == COLUMN_V1){
            chosen = sortByColumnV1;
        } else if (patternType == COLUMN_V2){
            chosen = sortByColumnV2;
        } else if (patternType == COLUMN_V3){
            chosen = sortByColumnV3;
        } else if (patternType == ROW){
            chosen = sortByRow;
        } else if (patternType == ROW_V1){
            chosen = sortByRowV1;
        } else if (patternType == ROW_V2){
            chosen = sortByRowV2;
        } else if (patternType == ROW_V3){
            chosen = sortByRowV3;
        } else if (patternType == SNAKE){
            chosen = sortBySnake;
        } else if (patternType == SNAKE_V1){
            chosen = sortBySnakeV1;
        } else if (patternType == SNAKE_V2){
            chosen = sortBySnakeV2;
        } else if (patternType == SNAKE_V3){
            chosen = sortBySnakeV3;
        } else if (patternType == VERTICAL_SNAKE){
            chosen = sortByVerticalSnake;
        } else if (patternType == VERTICAL_SNAKE_V1){
            chosen = sortByVerticalSnakeV1;
        } else if (patternType == VERTICAL_SNAKE_V2){
            chosen = sortByVerticalSnakeV2;
        } else if (patternType == VERTICAL_SNAKE_V3){
            chosen = sortByVerticalSnakeV3;
        } else if (patternType == HORIZONTAL_TWO_COLUMN_SNAKE){
            chosen = sortByHorizontalTwoColumnSnake;
        } else if (patternType == HORIZONTAL_TWO_COLUMN_SNAKE_V1){
            chosen = sortByHorizontalTwoColumnSnakeV1;
        } else if (patternType == HORIZONTAL_TWO_COLUMN_SNAKE_V2){
            chosen = sortByHorizontalTwoColumnSnakeV2;
        } else {
            chosen = sortByHorizontalTwoColumnSnakeV3;
        }

        for (int slotNum : chosen) {
            if (patternMap.containsKey(slotNum)) {
                itemsSorted.add(patternMap.get(slotNum));
            }
        }

        return itemsSorted;
    }

    public static int[] getSortMethod(PatternType patternType){
        if (patternType == PatternType.COLUMN) {
            // For each slot number we desire to prioritize, check if there is a sprite item in the slot, if so, add it to our sorted list
            return sortByColumn;
        } else if (patternType == COLUMN_V1){
            return sortByColumnV1;
        } else if (patternType == COLUMN_V2){
            return sortByColumnV2;
        } else if (patternType == COLUMN_V3){
            return sortByColumnV3;
        } else if (patternType == ROW){
            return sortByRow;
        } else if (patternType == ROW_V1){
            return sortByRowV1;
        } else if (patternType == ROW_V2){
            return sortByRowV2;
        } else if (patternType == ROW_V3){
            return sortByRowV3;
        } else if (patternType == SNAKE){
            return sortBySnake;
        } else if (patternType == SNAKE_V1){
            return sortBySnakeV1;
        } else if (patternType == SNAKE_V2){
            return sortBySnakeV2;
        } else if (patternType == SNAKE_V3){
            return sortBySnakeV3;
        } else if (patternType == VERTICAL_SNAKE){
            return sortByVerticalSnake;
        } else if (patternType == VERTICAL_SNAKE_V1){
            return sortByVerticalSnakeV1;
        } else if (patternType == VERTICAL_SNAKE_V2){
            return sortByVerticalSnakeV2;
        } else if (patternType == VERTICAL_SNAKE_V3){
            return sortByVerticalSnakeV3;
        } else if (patternType == HORIZONTAL_TWO_COLUMN_SNAKE){
            return sortByHorizontalTwoColumnSnake;
        } else if (patternType == HORIZONTAL_TWO_COLUMN_SNAKE_V1){
            return sortByHorizontalTwoColumnSnakeV1;
        } else if (patternType == HORIZONTAL_TWO_COLUMN_SNAKE_V2){
            return sortByHorizontalTwoColumnSnakeV2;
        } else {
            return sortByHorizontalTwoColumnSnakeV3;
        }
    }

}