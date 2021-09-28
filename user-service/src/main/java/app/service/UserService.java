package app.service;

import app.entities.User;
import app.exception.UserCreateException;
import app.exception.UserNotFoundException;
import app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(User newUser) {
        if (userRepository.findAllByUserEmail(newUser.getEmail())==null){
            return userRepository.save(newUser);
        }
        else {
            throw new UserCreateException();
        }
    }

    public User updateUser(long id, User userDetails){
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(userDetails.getEmail());
                    user.setTemp(userDetails.getTemp());
                    user.setWeatherType(userDetails.getWeatherType());
                    user.setWind(userDetails.getWind());
                    user.setLat(userDetails.getLat());
                    user.setLon(userDetails.getLon());
                    user.setDays(userDetails.getDays());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}

