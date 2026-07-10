package entity;

import shared.UserRoleEnum;
import shared.UserStateEnum;

public class User implements Entity{
    private int id;
    private String login;
    private String name;
    private UserRoleEnum role;
    private UserStateEnum state;

    public User(int id, String login, String name, UserRoleEnum role, UserStateEnum state){
        this.id = id;
        this.login = login;
        this.name = name;
        this.role = role;
        this.state = state;
    }

    @Override
    public int getId() {
        return this.id;
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

    public void setRole(UserRoleEnum role){
        this.role = role;
    }

    public UserStateEnum getState(){
        return this.state;
    }

    public void setState(UserStateEnum state){
        this.state = state;
    }
}
