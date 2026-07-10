package repository;
import entity.Advertisement;
import entity.User;
import request.CreateAdRequest;
import response.AdResponse;
import shared.AdCategoryEnum;
import shared.Price;

import java.time.Instant;
import java.util.Optional;

public class AdvertisementRepository implements Repository<Advertisement, Integer> {
    @Override
    public void save(Advertisement advertisement) {
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(null);
    }

    public AdResponse createAd(CreateAdRequest ad){
        //        UUID uuid = UUID.randomUUID();
        //        this.id = uuid;
        return new AdResponse("", AdCategoryEnum.CLOTHES, "", "", new Price(10.0, null), Instant.now());
    }
}
