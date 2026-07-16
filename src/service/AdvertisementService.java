package service;
import request.*;
import response.AdResponse;
import shared.AdCategoryEnum;
import shared.Price;

import java.time.Instant;

public class AdvertisementService {
    public AdResponse createAdvertisement(CreateAdRequest ad) {
        return new AdResponse("", AdCategoryEnum.CLOTHES, "", "", new Price(10.0, null), Instant.now());
    }

    public AdResponse editAdvertisement(EditAdRequest ad) {
        return new AdResponse("", AdCategoryEnum.CLOTHES, "", "", new Price(10.0, null), Instant.now());
    }

    public AdResponse toggleAdvertisement(ToggleAdRequest ad) {
        return new AdResponse("", AdCategoryEnum.CLOTHES, "", "", new Price(10.0, null), Instant.now());
    }

    public AdResponse findAdvertisement(FindAdRequest ad) {
        return new AdResponse("", AdCategoryEnum.CLOTHES, "", "", new Price(10.0, null), Instant.now());
    }

    public AdResponse[] outputAdvertisements(OutputAdsListRequest ad) {
        AdResponse[] ads = new AdResponse[]{new AdResponse("", AdCategoryEnum.CLOTHES, "", "", new Price(10.0, null), Instant.now())};
        return ads;
    }
}