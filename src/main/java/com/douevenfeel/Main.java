package com.douevenfeel;

import com.douevenfeel.client.WeatherClient;

public class Main {
    public static void main(String[] args) {
        try {
            WeatherClient weatherClient = new WeatherClient();
            weatherClient.fetchWeather(55.7558, 37.6173, 10);
            weatherClient.getWeather();
            weatherClient.getTemperature();
            weatherClient.getAverageTemperature();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}