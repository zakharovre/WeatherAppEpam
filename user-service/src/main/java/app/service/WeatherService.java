package app.service;

import app.controller.MailController;
import app.dtos.ForecastDto;
import app.entities.User;
import app.dtos.WeatherDto;
import app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherService {
    Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private final MailController mailController;
    private final UserRepository userRepository;
    public  WeatherService(MailController mailController, UserRepository userRepository){
        this.mailController = mailController;
        this.userRepository = userRepository;
    }

    public void sendAllUsers(WeatherDto weatherDto){
        logger.info("Request to get data of all users in WeatherService.class");
        List<User> users = userRepository.findAll();
        for (User user: users) {
            getData(weatherDto, user);
        }
    }
    public String getData(WeatherDto weatherDto, User user){
        logger.info("Request to get forecast of weather");
        StringBuilder weather = new StringBuilder();
        if (user.getTemp()){
            weather.append(tempAnalysis(user.getDays(),weatherDto));
        }
        if (user.getWind()){
            weather.append(windSpeedAnalysis(user.getDays(),weatherDto));
        }
        if (user.getWeatherType()){
            weather.append(weatherTypeAnalysis(user.getDays(),weatherDto));
        }
        mailController.sendSimpleMessage(user.getEmail(),weather.toString());
        return "Mail Send";
    }
    private String tempAnalysis(int days, WeatherDto weatherDto){
        logger.info("Temperature Analysis Request");
        StringBuilder temp = new StringBuilder();
        temp.append("Temperature").append("\n");
        Double actualTemp = weatherDto.getTemp();
        List<ForecastDto> forecastDays = weatherDto.getForecast();
        for (int i = 0; i<days; i++){
            ForecastDto day = forecastDays.get(i);
            LocalDateTime localDateTime = day.getLocalDateTime();
            if (day.getTempDay()>actualTemp){
                temp.append(localDateTime.getDayOfMonth())
                        .append(" ").append(localDateTime.getMonth())
                        .append(" the daytime temperature will rise to ")
                        .append(day.getTempDay()).append("\n");
            } else if (day.getTempDay()<actualTemp){
                temp.append(localDateTime.getDayOfMonth())
                        .append(" ").append(localDateTime.getMonth())
                        .append(" the daytime temperature will drop to ")
                        .append(day.getTempDay()).append("\n");
            }
            if (day.getTempNight()>actualTemp){
                temp.append(localDateTime.getDayOfMonth())
                        .append(" ").append(localDateTime.getMonth())
                        .append(" the nighttime temperature will rise to ")
                        .append(day.getTempDay()).append("\n");
            } else if (day.getTempNight()<actualTemp){
                temp.append(localDateTime.getDayOfMonth())
                        .append(" ").append(localDateTime.getMonth())
                        .append(" the nighttime temperature will drop to ")
                        .append(day.getTempDay()).append("\n");
            }
        }
        return temp.toString();
    }
    private String windSpeedAnalysis(int days, WeatherDto weatherDto){
        logger.info("Wind Speed Analysis Request");
        StringBuilder wind = new StringBuilder();
        wind.append("Wind Speed").append("\n");
        int actualWindSpeed = weatherDto.getWindSpeed();
        List<ForecastDto> forecastDays = weatherDto.getForecast();
        for (int i = 0; i<days; i++){
            ForecastDto day = forecastDays.get(i);
            LocalDateTime localDateTime = day.getLocalDateTime();
            if (day.getWindSpeed()>actualWindSpeed){
                wind.append(localDateTime.getDayOfMonth())
                        .append(" ").append(localDateTime.getMonth())
                        .append(" wind speed will increase to ")
                        .append(day.getWindSpeed()).append("\n");
            } else if (day.getWindSpeed()<actualWindSpeed){
                wind.append(localDateTime.getDayOfMonth())
                        .append(" ").append(localDateTime.getMonth())
                        .append(" wind speed will decrease to ")
                        .append(day.getWindSpeed()).append("\n");
            }
        }
        return wind.toString();
    }
    private String weatherTypeAnalysis(int days, WeatherDto weatherDto){
        logger.info("Weather Analysis Request");
        StringBuilder weather = new StringBuilder();
        weather.append("WeatherType").append("\n");
        String actualWeatherType = weatherDto.getWeather();
        List<ForecastDto> forecastDays = weatherDto.getForecast();
        for (int i = 0; i<days; i++){
            ForecastDto day = forecastDays.get(i);
            LocalDateTime localDateTime = day.getLocalDateTime();
            if (!day.getWeather().equals(actualWeatherType)){
                weather.append(localDateTime.getDayOfMonth())
                        .append(" ").append(localDateTime.getMonth())
                        .append(" will be ")
                        .append(day.getWeather()).append("\n");
            }
        }
        return weather.toString();
    }
}
