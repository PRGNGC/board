package request;

import shared.AdCategoryEnum;
import shared.Price;

public class SearchAdRequest {
    private AdCategoryEnum category;
    private String title;
    private String description;
    private Price price;

    public SearchAdRequest() {}

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

    public void setDescription(String description){ this.description = description; }

    public Price getPrice(){
        return this.price;
    }

    public void setPrice(Price price){
        this.price = price;
    }
}
