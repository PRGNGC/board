package request;
import java.util.Date;
import java.util.Random;

public class CreateAdRequest {
    private int id;
    public String author;
    public String category;
    public String title;
    public String description;
    public float price;
    public Date date;
    public Boolean isActive;

    public CreateAdRequest(String author, String category, String title, String description, float price, Date date) {
        Random random = new Random();
        this.id = random.nextInt(1000001);
        this.author = author;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.date = date;
        this.isActive = true;
    }
}
