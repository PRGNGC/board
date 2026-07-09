package facade;
import request.CreateAdRequest;
import request.EditAdRequest;
import request.FindAdRequest;
import request.ToggleAdRequest;
import service.AdvertisementService;
import java.util.Date;

public class ApplicationFacade {
    private String currentMode = "0";

    public void showMainMenu(){
        IO.println("Выберите тип операции:");
        IO.println("Создать объявление - 1");
        IO.println("Редактировать объявление - 2");
        IO.println("Изменить статус объявления - 3");
        IO.println("Поиск объявления - 4");
        IO.println("Завершить работу - exit");
        IO.println();
    }

    public void createAdvertisement(){
        IO.println("Создание объявления...");
        CreateAdRequest newAd = new CreateAdRequest("", "", "", "", 0, new Date());
        AdvertisementService adService = new AdvertisementService();
        adService.createAdvertisement(newAd);
        currentMode = currentMode.substring(0, currentMode.length() - 1);
        IO.println("Объявление создано!");
        IO.println();
    }

    public void editAdvertisement(Integer id){
        IO.println("Редактирование объявления...");
        EditAdRequest editedAd = new EditAdRequest();
        AdvertisementService adService = new AdvertisementService();
        adService.editAdvertisement(editedAd);
        currentMode = currentMode.substring(0, currentMode.length() - 1);
        IO.println("Объявление отредактировано!");
        IO.println();
    }

    public void toggleAdvertisement(){
        IO.println("Изменение статуса объявления...");
        ToggleAdRequest toggledAd = new ToggleAdRequest();
        AdvertisementService adService = new AdvertisementService();
        adService.toggleAdvertisement(toggledAd);
        currentMode = currentMode.substring(0, currentMode.length() - 1);
        IO.println("Статус объявления изменён!");
        IO.println();
    }

    public void findAdvertisement(){
        IO.println("Поиск объявления...");
        FindAdRequest findAd = new FindAdRequest();
        AdvertisementService adService = new AdvertisementService();
        adService.findAdvertisement(findAd);
        currentMode = currentMode.substring(0, currentMode.length() - 1);
        IO.println("Объявление найдено!");
        IO.println();
    }

    public void appLaunch(){
        showMainMenu();

        while(true){
            IO.print("Введите число: ");
            currentMode = IO.readln();
            if(currentMode.equals("ex")) break;

            switch (currentMode){
                case "1":
                    createAdvertisement();
                    break;
                case "2":
                    editAdvertisement(1);
                    break;
                case "3":
                    toggleAdvertisement();
                    break;
                case "4":
                    findAdvertisement();
                    break;
                default:
                    IO.println("Неизвестная команда!");
                    IO.println();
                    break;
            }
        }
    }
}
