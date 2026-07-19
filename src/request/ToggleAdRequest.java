package request;

import shared.AdStateEnum;
import shared.UserRoleEnum;
import java.util.UUID;

public class ToggleAdRequest {
    private UUID id;
    private AdStateEnum state;
    private UserRoleEnum changer;

    public ToggleAdRequest() {}

    public UUID getId(){
        return this.id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public AdStateEnum getState(){
        return this.state;
    }

    public void setState(AdStateEnum state){
        this.state = state;
    }

    public UserRoleEnum getChanger(){
        return this.changer;
    }

    public void setChanger(UserRoleEnum changer){
        this.changer = changer;
    }
}
