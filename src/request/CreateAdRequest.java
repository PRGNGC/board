package request;
import shared.AdCategoryEnum;
import shared.Price;

import java.time.Instant;

public class CreateAdRequest {
    private String authorId;
    private AdCategoryEnum category;
    private String title;
    private String description;
    private Price price;
    private Instant createdAt;

    public CreateAdRequest(String authorId, AdCategoryEnum category, String title, String description, Price price, Instant createdAt) {
        this.authorId = authorId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
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
}
