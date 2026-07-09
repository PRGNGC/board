package service;
import entity.Advertisement;
import repository.AdvertisementRepository;
import request.CreateAdRequest;
import request.EditAdRequest;
import request.FindAdRequest;
import request.ToggleAdRequest;

import java.util.Date;

public class AdvertisementService {
    public Advertisement createAdvertisement(CreateAdRequest ad){
        return new Advertisement(1, "", "", "", "", 0, new Date(), false, "");
    }

    public Advertisement editAdvertisement(EditAdRequest ad){
        return new Advertisement(1, "", "", "", "", 0, new Date(), false, "");
    }

    public Advertisement toggleAdvertisement(ToggleAdRequest ad){
        return new Advertisement(1, "", "", "", "", 0, new Date(), false, "");
    }

    public Advertisement findAdvertisement(FindAdRequest ad){
        return new Advertisement(1, "", "", "", "", 0, new Date(), false, "");
    }
}
