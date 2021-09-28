package app.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "useremail")
    private String userEmail;

    @Column(name = "temp")
    private boolean temp;

    @Column(name = "wind")
    private boolean wind;

    @Column(name = "weather_type")
    private boolean weatherType;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lon")
    private double lon;

    @Column(name = "days")
    private int days;

    public User() {
    }

    public User(String userEmail, boolean temp, boolean wind, boolean weatherType, double lat, double lon, int days) {
        this.userEmail = userEmail;
        this.temp = temp;
        this.wind = wind;
        this.weatherType = weatherType;
        this.lat = lat;
        this.lon = lon;
        this.days = days;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean getTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public boolean getWind() {
        return wind;
    }

    public void setWind(boolean wind) {
        this.wind = wind;
    }

    public boolean getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(boolean weatherType) {
        this.weatherType = weatherType;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", userEmail=" + this.userEmail + ", temp=" + this.temp + ", wind="
                + this.wind + ", weatherType=" + this.weatherType + ", days=" + this.days +", lat|lon=" + this.lat + '|'+this.lon+ '}';
    }

}

