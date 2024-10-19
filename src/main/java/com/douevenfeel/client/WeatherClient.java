package com.douevenfeel.client;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherClient {
    final private HttpClient client = HttpClient.newHttpClient();
    private JSONObject weather;

    public void fetchWeather(double lat, double lon, int limit) throws IOException, InterruptedException {
        String key = "***";
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(String.format("https://api.weather.yandex.ru/v2/forecast?lat=%s&lon=%s&limit=%s", lat, lon, limit)))
                .header("X-Yandex-Weather-Key", key)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            System.out.println("Произошла ошибка: " + response.statusCode());
            return;
        }
        weather = new JSONObject(response.body());
    }

    public void getWeather() {
        System.out.println("body: " + this.weather.toString(4));
    }

    public void getTemperature() throws Exception {
        try {
            String temperature = this.weather.getJSONObject("fact").get("temp").toString();
            System.out.println("Температура: " + temperature);
        } catch (Exception e) {
            System.out.println("Ошибка при получении температуры");
            System.out.println(e.getMessage());
        }
    }

    public void getAverageTemperature() {
        try {
            int sum = 0;
            JSONArray forecasts = this.weather.getJSONArray("forecasts");
            int count = forecasts.length();
            for (int index = 0; index < forecasts.length(); index++) {
                JSONArray hours = forecasts.getJSONObject(index).getJSONArray("hours");
                count += hours.length();
                for (int hourIndex = 0; hourIndex < hours.length(); hourIndex++) {
                    sum += (int) hours.getJSONObject(hourIndex).get("temp");
                }
            }
            System.out.println("Средняя температура: " + sum / count);
        } catch (Exception e) {
            System.out.println("Ошибка при вычислении средней температуры");
            System.out.println(e.getMessage());
        }
    }
}
