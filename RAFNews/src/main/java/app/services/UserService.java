package app.services;

import app.entities.User;
import app.repositories.user.SqlUserRepository;
import app.repositories.user.UserRepository;

public class UserService {

    private static final UserRepository userRepository = new SqlUserRepository();

    public static User addUser(User user) {
        return userRepository.addUser(user);
    }

    public static User findUser(String email) {
        return userRepository.findUserByEmail(email);
    }

}
