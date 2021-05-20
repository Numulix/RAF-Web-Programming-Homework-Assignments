package app.repositories.user;

import app.entities.User;

import java.util.List;

public interface UserRepository {
    public User addUser(User user);
    public User findUserByEmail(String email);
    public User editUser(User user);
    public List<User> listAllUsers();
}
