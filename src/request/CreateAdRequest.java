package request;

import shared.AdCategoryEnum;
import shared.Price;
import java.util.UUID;

public class CreateAdRequest {
    private UUID authorId;
    private String title;
    private String description;
    private Price price;
    private AdCategoryEnum category;

    public CreateAdRequest() {}

    public UUID getAuthorId(){
        return this.authorId;
    }

    public void setAuthorId(UUID authorId){ this.authorId = authorId; }

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
}
