package request;

import shared.UserStateEnum;
import java.util.UUID;

public class ToggleUserRequest {
    private UUID id;
    private UserStateEnum state;

    public ToggleUserRequest() {}

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public UserStateEnum getState(){
        return this.state;
    }

    public void setState(UserStateEnum state){ this.state = state; }
}
