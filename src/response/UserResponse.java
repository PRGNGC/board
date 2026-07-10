package response;

public class UserResponse {
    private String login;
    private String name;

    public UserResponse(String login, String name){
        this.login = login;
        this.name = name;
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
}
