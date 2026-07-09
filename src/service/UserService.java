package service;
import entity.User;
import repository.UserRepository;

public class UserService {
    public User authUser(){
        return new User(1, "", "", false, false);
    }

    public User regUser(){
        return new User(1, "", "", false, false);
    }

    public User createUser(){
        return new User(1, "", "", false, false);
    }
}
