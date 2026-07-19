package response;

import shared.AdCategoryEnum;
import shared.Price;
import java.util.UUID;

public class CreateAdResponse {
    private UUID id;
    private String title;
    private String description;
    private AdCategoryEnum category;
    private Price price;

    public CreateAdResponse(){}

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
}
