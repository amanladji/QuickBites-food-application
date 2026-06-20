package com.tap.DAO;
import com.tap.model.User;
import java.util.List;

public interface UserDAO {
    void addUser(User u);
    void updateUser(User u);
    void removeUser(int id);
    User getUser(int id);
    User getUserByEmail(String email);
    List<User> getAll();
}
