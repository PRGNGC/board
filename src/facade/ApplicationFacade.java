package facade;
import request.*;
import service.AdvertisementService;
import shared.AdCategoryEnum;
import shared.AppModesEnum;
import shared.Price;

import java.time.Instant;

public class ApplicationFacade {
    private AdvertisementService adService;
//    private AppModesEnum currentMode = AppModesEnum.IDLE;
    private AppModesEnum currentMode = AppModesEnum.UNKNOWN;

    public ApplicationFacade(AdvertisementService adService){
        this.adService = adService;
    }

    public void showMainMenu(){
        IO.println("Выберите тип операции:");
        IO.println("Создать объявление - 1");
        IO.println("Редактировать объявление - 2");
        IO.println("Изменить статус объявления - 3");
        IO.println("Поиск объявления - 4");
        IO.println("Посмотреть список объявлений - 5");
        IO.println("Завершить работу - ex");
        IO.println();
    }

    public void createAdvertisement(){
        IO.println("Создание объявления...");
        CreateAdRequest newAd = new CreateAdRequest("", AdCategoryEnum.CLOTHES, "", "", new Price(10.0, null), Instant.now());
//        AdvertisementService adService = new AdvertisementService();
        this.adService.createAdvertisement(newAd);
//        currentMode = currentMode.substring(0, currentMode.length() - 1);
        IO.println("Объявление создано!");
        IO.println();
    }

    public void editAdvertisement(Integer id){
        IO.println("Редактирование объявления...");
        EditAdRequest editedAd = new EditAdRequest();
//        AdvertisementService adService = new AdvertisementService();
        this.adService.editAdvertisement(editedAd);
//        currentMode = currentMode.substring(0, currentMode.length() - 1);
        IO.println("Объявление отредактировано!");
        IO.println();
    }

    public void toggleAdvertisement(){
        IO.println("Изменение статуса объявления...");
        ToggleAdRequest toggledAd = new ToggleAdRequest();
//        AdvertisementService adService = new AdvertisementService();
        this.adService.toggleAdvertisement(toggledAd);
//        currentMode = currentMode.substring(0, currentMode.length() - 1);
        IO.println("Статус объявления изменён!");
        IO.println();
    }

    public void findAdvertisement(){
        IO.println("Поиск объявления...");
        FindAdRequest findAd = new FindAdRequest();
//        AdvertisementService adService = new AdvertisementService();
        this.adService.findAdvertisement(findAd);
//        currentMode = currentMode.substring(0, currentMode.length() - 1);
        IO.println("Объявление найдено!");
        IO.println();
    }

    public void outputAdvertisementsList(){
        IO.println("Вывод списка объявлений...");
        OutputAdsListRequest outputAds = new OutputAdsListRequest();
//        AdvertisementService adService = new AdvertisementService();
        this.adService.outputAdvertisements(outputAds);
//        currentMode = currentMode.substring(0, currentMode.length() - 1);
        IO.println("Объявления выведены!");
        IO.println();
    }

    public void appLaunch(){
        showMainMenu();

        while(true){
            IO.print("Введите число: ");
            currentMode = AppModesEnum.fromString(IO.readln());
            if(currentMode == AppModesEnum.EXIT) break;

            switch (currentMode){
                case CREATE:
                    createAdvertisement();
                    break;
                case EDIT:
                    editAdvertisement(1);
                    break;
                case TOGGLE:
                    toggleAdvertisement();
                    break;
                case SEARCH:
                    findAdvertisement();
                    break;
                case LIST_OUTPUT:
                    outputAdvertisementsList();
                    break;
                default:
                    IO.println("Неизвестная команда!");
                    IO.println();
                    break;
            }
        }
    }
}
