package response;

import shared.AdCategoryEnum;
import shared.AdStateEnum;
import shared.Price;
import shared.UserRoleEnum;
import java.util.UUID;

public class FindAdResponse {
    private UUID id;
    private String title;
    private String description;
    private AdCategoryEnum category;
    private Price price;
    private AdStateEnum state;
    private UserRoleEnum lastChangedBy;

    public FindAdResponse(){}

    public UUID getId(){
        return this.id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public AdCategoryEnum getCategory(){
        return this.category;
    }

    public void setCategory(AdCategoryEnum category){
        this.category = category;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Price getPrice(){
        return this.price;
    }

    public void setPrice(Price price){
        this.price = price;
    }

    public AdStateEnum getState(){
        return this.state;
    }

    public void setState(AdStateEnum state){
        this.state = state;
    }

    public UserRoleEnum getLastChanger(){
        return this.lastChangedBy;
    }

    public void setLastChanger(UserRoleEnum lastChangedBy){ this.lastChangedBy = lastChangedBy; }
}
