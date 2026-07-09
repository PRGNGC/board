package entity;

public class User implements Entity{
    public int id;
    private String login;
    public String name;
    public Boolean isAdmin;
    public Boolean isActive;

    public User(int id, String login, String name, Boolean isAdmin, Boolean isActive){
        this.id = id;
        this.login = login;
        this.name = name;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
