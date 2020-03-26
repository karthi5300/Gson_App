package com.karthick.gsonapp;

public class WeatherData {

    private long dt;
    private String name;

    private Weather[] weather;
    private Wind wind;
    private Coord coord;
    private Main main;
    private Sys sys;

    public WeatherData(long dt, String name, Weather[] weather, Wind wind, Coord coord, Main main, Sys sys) {
        this.dt = dt;
        this.name = name;
        this.weather = weather;
        this.wind = wind;
        this.coord = coord;
        this.main = main;
        this.sys = sys;
    }

    public long getDt() {
        return dt;
    }

    public String getName() {
        return name;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public Coord getCoord() {
        return coord;
    }

    public Main getMain() {
        return main;
    }

    public Sys getSys() {
        return sys;
    }
}

