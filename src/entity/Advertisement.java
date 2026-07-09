package entity;
import java.util.Date;
import java.util.Random;

public class Advertisement implements Entity{
    public int id;
    private String author;
    private String category;
    private String title;
    private String description;
    private float price;
    private Date createdAt;
    private Boolean isActive;
    private String lastChangedBy;

    public Advertisement(int id, String author, String category, String title, String description, float price, Date createdAt, Boolean isActive, String lastChangedBy) {
        this.id = id;
        this.author = author;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
        this.isActive = isActive;
        this.lastChangedBy = lastChangedBy;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
