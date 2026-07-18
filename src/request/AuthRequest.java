package request;

public class AuthRequest {
    private String login;
    private String name;

    public AuthRequest(){}

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getLogin(){
        return this.login;
    }

    public void setLogin(String login){ this.login = login; }
}
