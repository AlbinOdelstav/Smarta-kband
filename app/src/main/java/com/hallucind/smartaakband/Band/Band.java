package com.hallucind.smartaakband.Band;

/**
 * Created by albin on 2018-04-22.
 */

public class Band {
    private float color;
    private String name;
    private int battery;

    public Band(String name, float color) {
        this.name = name;
        this.color = color;
        battery = 100; // Ger bandets batteri 100% för demonstration
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getBattery() {
        return battery;
    }

    public float getcolorID() {
        return color;
    }
}