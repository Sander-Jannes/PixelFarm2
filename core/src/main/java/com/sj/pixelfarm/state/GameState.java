package com.sj.pixelfarm.state;


import com.badlogic.gdx.utils.Array;
import com.sj.pixelfarm.world.FieldGroup;

public final class GameState {

    /* Represents the amount of money the player currently has in the game. */
    public float money;

    /* Represents the player's current experience points in the game. */
    public int xp;

    /* Represents the experience points (XP) target that the player needs to achieve */
    public int xpGoal;

    /* Represents the current level of the player in the game. */
    public int level;

    /* Represents the current in-game day. */
    public int currentDay;

    public float currentTime;

    public final Array<FieldGroup> unlockedFields = new Array<>();
}
