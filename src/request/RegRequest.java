package request;

public class RegRequest {
    private String login;
    private String name;

    public RegRequest(){}

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getLogin(){
        return this.login;
    }

    public void setLogin(String login){
        this.login = login;
    }
}
