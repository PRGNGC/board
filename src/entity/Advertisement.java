package entity;
import shared.AdCategoryEnum;
import shared.AdStateEnum;
import shared.Price;

import java.time.Instant;

public class Advertisement implements Entity{
    private int id;
    private String authorId;
    private AdCategoryEnum category;
    private String title;
    private String description;
    private Price price;
    private Instant createdAt;
    private AdStateEnum state;
    private String lastChangedBy;
    private AdStateEnum lastStateChangedBy;

    public Advertisement(int id, String authorId, AdCategoryEnum category, String title, String description, Price price, Instant createdAt, AdStateEnum state, String lastChangedBy) {
        this.id = id;
        this.authorId = authorId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
        this.state = state;
        this.lastChangedBy = lastChangedBy;
        this.lastStateChangedBy = null;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String getAuthorId(){
        return this.authorId;
    }

    public void setAuthorId(String authorId){
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

    public String getLastChanger(){
        return this.lastChangedBy;
    }

    public void setLastChanger(String lastChangedBy){
        this.lastChangedBy = lastChangedBy;
    }

    public AdStateEnum getLastChangedState(){
        return this.lastStateChangedBy;
    }

    public void setLastChangedState(AdStateEnum lastStateChangedBy){
        this.lastStateChangedBy = lastStateChangedBy;
    }
}
