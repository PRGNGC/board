package service;

import repository.AdvertisementRepository;
import request.*;
import response.*;
import java.util.Optional;

public class AdvertisementService {
    private final AdvertisementRepository adRep;

    public AdvertisementService(){
        this.adRep = new AdvertisementRepository();
    }

    public Optional<CreateAdResponse> createAdvertisement(CreateAdRequest ad) { return adRep.createAd(ad); }

    public Optional<EditAdResponse> editAdvertisement(EditAdRequest ad) { return adRep.editAd(ad); }

    public Optional<ToggleAdResponse> toggleAdvertisement(ToggleAdRequest ad) {
        return adRep.toggleAd(ad);
    }

    public Optional<FindAdResponse> findAdvertisement(FindAdRequest ad) { return adRep.findAd(ad); }

    public Optional<SearchAdResponse> searchAdvertisement(SearchAdRequest ad) { return adRep.searchAd(ad); }

    public Optional<OutputAdResponse> outputAdvertisements(OutputAdsListRequest ad) { return adRep.outputAds(ad); }
}