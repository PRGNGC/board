package entity;

import shared.AdCategoryEnum;
import shared.AdStateEnum;
import shared.Price;
import shared.UserRoleEnum;
import java.time.Instant;
import java.util.UUID;

public class Advertisement implements Entity{
    private UUID id;
    private UUID authorId;
    private AdCategoryEnum category;
    private String title;
    private String description;
    private Price price;
    private Instant createdAt;
    private AdStateEnum state;
    private UserRoleEnum lastChangedBy;
    private AdStateEnum lastStateChangedBy;

    public Advertisement() {}

    @Override
    public UUID getId() { return this.id; }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAuthorId(){
        return this.authorId;
    }

    public void setAuthorId(UUID authorId){
        this.authorId = authorId;
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

    public Instant getDate(){
        return this.createdAt;
    }

    public void setDate(Instant createdAt){
        this.createdAt = createdAt;
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

    public void setLastChanger(UserRoleEnum lastChangedBy){
        this.lastChangedBy = lastChangedBy;
    }

    public AdStateEnum getLastChangedState(){
        return this.lastStateChangedBy;
    }

    public void setLastChangedState(AdStateEnum lastStateChangedBy){
        this.lastStateChangedBy = lastStateChangedBy;
    }
}
