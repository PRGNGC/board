package request;
import java.util.Random;

public class CreateUserRequest {
    public int id;
    private String login;
    public String name;
    public Boolean isAdmin;
    public Boolean isActive;

    public CreateUserRequest(int id, String login, String name){
        Random random = new Random();
        this.id = random.nextInt(1000001);
        this.login = login;
        this.name = name;
        this.isAdmin = false;
        this.isActive = true;
    }
}
