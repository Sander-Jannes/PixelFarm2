package com.sj.pixelfarm.state;


import com.badlogic.gdx.utils.Array;
import com.sj.pixelfarm.world.FieldGroup;

public final class GameState {

    /* Represents the amount of money the player currently has in the game. */
    public float money = 20;

    /* Represents the player's current experience points in the game. */
    public int xp = 0;

    /* Represents the XP target that the player needs to achieve */
    public int xpGoal;

    /* Represents the current level of the player in the game. */
    public int level = 7;

    public final Array<FieldGroup> unlockedFields = new Array<>();
}
