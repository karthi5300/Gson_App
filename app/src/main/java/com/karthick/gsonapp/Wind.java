package com.karthick.gsonapp;

public class Wind {

    private double speed;
    private int deg;

    public Wind(double speed, int deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public double getSpeed() {
        return speed;
    }

    public int getDeg() {
        return deg;
    }
}
