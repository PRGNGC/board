import facade.ApplicationFacade;
import service.AdvertisementService;

void main() {
    AdvertisementService adService = new AdvertisementService();
    ApplicationFacade a = new ApplicationFacade(adService);
    a.appLaunch();
}
