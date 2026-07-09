package repository;
import entity.Advertisement;

public class AdvertisementRepository implements Repository<Advertisement, Integer> {
    @Override
    public void save(Advertisement advertisement) {
    }

    @Override
    public int findById(Integer id) {
        return 0;
    }
}
