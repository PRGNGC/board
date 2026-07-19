package service;

import repository.UserRepository;
import request.*;
import response.*;

import java.util.Optional;

public class UserService {
    private final UserRepository userRep;

    public UserService(){
        this.userRep = new UserRepository();
    }

    public Optional<AuthUserResponse> authUser(AuthRequest user){
        return userRep.authUser(user);
    }

    public Optional<RegUserResponse> regUser(RegRequest user){
        return userRep.regUser(user);
    }

    public Optional<FindUserResponse> findUser(FindUserRequest ad) { return userRep.findUser(ad); }

    public Optional<ToggleUserResponse> toggleUser(ToggleUserRequest user) { return userRep.toggleUser(user); }
}
