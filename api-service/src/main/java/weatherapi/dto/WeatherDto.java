package weatherapi.dto;

import java.util.List;

public class WeatherDto {

    private double temp;
    private int pressure;
    private int humidity;
    private int windSpeed;
    private int windDeg;
    private String weather;
    private String weatherDescription;
    private List<ForecastDto> forecast;

    public WeatherDto() {
    }

    public WeatherDto(double temp, int pressure, int humidity, int windSpeed, int windDeg, String weather, String weatherDescription, List<ForecastDto> forecast) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.weather = weather;
        this.weatherDescription = weatherDescription;
        this.forecast = forecast;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(int windDeg) {
        this.windDeg = windDeg;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public List<ForecastDto> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastDto> forecast) {
        this.forecast = forecast;
    }
}
