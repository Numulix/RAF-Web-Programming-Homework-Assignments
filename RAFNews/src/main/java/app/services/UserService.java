package app.services;

import app.entities.User;
import app.repositories.user.SqlUserRepository;
import app.repositories.user.UserRepository;

import java.util.List;

public class UserService {

    private static final UserRepository userRepository = new SqlUserRepository();

    public static User addUser(User user) {
        return userRepository.addUser(user);
    }

    public static User findUser(Integer id) {
        return userRepository.findUserById(id);
    }

    public static User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public static List<User> allUsers() {
        return userRepository.listAllUsers();
    }

    public static User editUser(User user) {
        return userRepository.editUser(user);
    }


}
