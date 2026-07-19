package response;

import shared.UserRoleEnum;
import shared.UserStateEnum;
import java.util.UUID;

public class ToggleUserResponse {
    private UUID id;
    private String login;
    private String name;
    private UserRoleEnum role;
    private UserStateEnum state;

    public ToggleUserResponse(){}

    public UUID getId(){
        return this.id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getLogin(){
        return this.login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public UserRoleEnum getRole(){
        return this.role;
    }

    public void setRole(UserRoleEnum role){ this.role = role; }

    public UserStateEnum getState(){
        return this.state;
    }

    public void setState(UserStateEnum state){ this.state = state; }
}
