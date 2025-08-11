package com.sj.pixelfarm;

import com.badlogic.gdx.utils.OrderedMap;
import com.sj.pixelfarm.input.events.EventType;
import com.sj.pixelfarm.core.Events;


public class GameClock {

    private static final float SECONDS_IN_A_DAY = 86400f;
    private static final float updateInterval = 300f;
    private static final float timeScale = 96;
    public static final OrderedMap<String, String> dayNameMap = new OrderedMap<>();

    static {
        dayNameMap.put("Mon", "Monday");
        dayNameMap.put("Tue", "Tuesday");
        dayNameMap.put("Wed", "Wednesday");
        dayNameMap.put("Thu", "Thursday");
        dayNameMap.put("Fri", "Friday");
    }

    /* Timescale 1 is equal to real time, so 5 minutes passed in game after 5 minutes real */
    /* 96 is normal */
    private float elapsedTime = 43200f;
    private int currentDay = 1;
    private float timeSinceLastUpdate = 0f;

    public void update(float delta) {
        elapsedTime += delta * timeScale;
        timeSinceLastUpdate += delta * timeScale;

        if (timeSinceLastUpdate >= updateInterval) {
            timeSinceLastUpdate -= updateInterval;
            Events.fire(new EventType.UpdateClockEvent());
        }

        if (elapsedTime >= SECONDS_IN_A_DAY) {
            elapsedTime -= SECONDS_IN_A_DAY;
            currentDay++;
            Events.fire(new EventType.NewDayEvent());
            Events.fire(new EventType.StartCar());
        }
    }

    public String getDayFullName() {
        return dayNameMap.values().toArray().get((currentDay - 1) % dayNameMap.size);
    }

    public int getCurrentDayInt() {
        return currentDay;
    }

    public String getFormatted() {
        return getDayFullName() + " - " + getCurrentDayInt();
    }

    public String getTime() {
        int totalSeconds = (int) elapsedTime;
        int hours = (totalSeconds % 86400) / 3600;
        int minutes = (totalSeconds % 3600) / 60;

        return (hours < 10 ? "0" : "") + hours + ":" +
            (minutes < 10 ? "0" : "") + minutes;
    }
}
