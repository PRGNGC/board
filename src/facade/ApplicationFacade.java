package facade;

import entity.User;
import request.*;
import response.*;
import service.AdvertisementService;
import service.UserService;
import shared.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ApplicationFacade {
    private final AdvertisementService adService;
    private final UserService userService;
    private AppModesEnum currentMode = AppModesEnum.UNKNOWN;
    private InitialAppModesEnum initialCurrentMode = InitialAppModesEnum.UNKNOWN;
    private AdminModesEnum adminCurrentMode = AdminModesEnum.UNKNOWN;
    private User currentUser = null;

    public ApplicationFacade(){
        this.adService = new AdvertisementService();
        this.userService = new UserService();
    }

    public void showAdminMenu(){
        IO.println("*пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ: ");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ:");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - 1");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - 2");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ - ex");
        IO.println();
    }

    public void showStartMenu(){
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ:");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - 1");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - 2");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ - ex");
        IO.println();
    }

    public void showMainMenu(){
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ:");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - 1");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - 2");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - 3");
        IO.println("пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - 4");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - 5");
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ - ex");
        IO.println();
    }

    public void createAdvertisement(){
//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        CreateAdRequest newAd = new CreateAdRequest();

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        newAd.setAuthorId(currentUser.getId());
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ:");

        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - ");
        newAd.setTitle(IO.readln());

        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - ");
        newAd.setDescription(IO.readln());

        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - ");
        newAd.setCategory(AdCategoryEnum.fromString(IO.readln()));

        IO.print("пїЅпїЅпїЅпїЅ - ");
        String inputPrice = IO.readln();
//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ
        try {
            double inputToDouble = Double.parseDouble(inputPrice);
            newAd.setPrice(Price.ofNumber(inputToDouble));
        } catch(NumberFormatException e){
            newAd.setPrice(Price.ofText(TextPriceEnum.fromString(inputPrice)));
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<CreateAdResponse> result = this.adService.createAdvertisement(newAd);

        if(result.isPresent()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
        } else {
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅ!");
            IO.println();
        }

        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void editAdvertisement(){
//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        FindAdRequest findAd = new FindAdRequest();

        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ: ");

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ uuid
        try{
            String adArticle = IO.readln();
            UUID uuid = UUID.fromString(adArticle);
            findAd.setId(uuid);
        } catch (IllegalArgumentException exception){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        findAd.setAuthorId(currentUser.getId());

//        пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<FindAdResponse> findResult = this.adService.findAdvertisement(findAd);

//        пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ - пїЅпїЅпїЅпїЅпїЅ
        if(findResult.isEmpty()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        FindAdResponse foundedAd = findResult.get();

//        пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        EditAdRequest editedAd = new EditAdRequest();
        editedAd.setTitle(foundedAd.getTitle());
        editedAd.setId(foundedAd.getId());
        editedAd.setDescription(foundedAd.getDescription());
        editedAd.setCategory(foundedAd.getCategory());
        editedAd.setPrice(foundedAd.getPrice());

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ: (*пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ*)");
        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ(" + foundedAd.getTitle() + ") - ");
        String newTitle = IO.readln();
        if(!newTitle.isEmpty()) editedAd.setTitle(newTitle);

        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ(" + foundedAd.getDescription() + ") - ");
        String newDesc = IO.readln();
        if(!newDesc.isEmpty()) editedAd.setDescription(newDesc);

        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ(" + foundedAd.getCategory().getText() + ") - ");
        String newCategory = IO.readln();
        if(!newCategory.isEmpty()) editedAd.setCategory(AdCategoryEnum.fromString(newCategory));

        IO.print("пїЅпїЅпїЅпїЅ(" + (foundedAd.getPrice().isText() ? foundedAd.getPrice().getStringValue().getText() : foundedAd.getPrice().getNumericValue()) + ") - ");
        String newPrice = IO.readln();
//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ
        if(!newPrice.isEmpty()) {
            try {
                double inputToDouble = Double.parseDouble(newPrice);
                editedAd.setPrice(Price.ofNumber(inputToDouble));
            } catch(NumberFormatException e){
                editedAd.setPrice(Price.ofText(TextPriceEnum.fromString(newPrice)));
            }
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<EditAdResponse> editResult = this.adService.editAdvertisement(editedAd);

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        if(editResult.isPresent()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
        } else {
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅ!");
            IO.println();
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void toggleAdvertisement(){
//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ: ");

//        пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        FindAdRequest findAd = new FindAdRequest();

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ uuid
        try{
            String adArticle = IO.readln();
            UUID uuid = UUID.fromString(adArticle);
            findAd.setId(uuid);
        } catch (IllegalArgumentException exception){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        if(currentUser.getRole() != UserRoleEnum.ADMIN) findAd.setAuthorId(currentUser.getId());

//        пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<FindAdResponse> findResult = this.adService.findAdvertisement(findAd);

//        пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ - пїЅпїЅпїЅпїЅпїЅ
        if(findResult.isEmpty()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        FindAdResponse foundedAd = findResult.get();

        if(foundedAd.getLastChanger() == UserRoleEnum.ADMIN && foundedAd.getState() == AdStateEnum.IDLE){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//      пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        ToggleAdRequest toggledAd = new ToggleAdRequest();
        toggledAd.setState(foundedAd.getState());
        toggledAd.setId(foundedAd.getId());
        toggledAd.setChanger(currentUser.getRole());

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        AdStateEnum newState;
        if(foundedAd.getState().getText().equals("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ")){
            newState = AdStateEnum.IDLE;
        } else {
            newState = AdStateEnum.ACTIVE;
        }

        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + foundedAd.getState().getText());
        IO.println("пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + newState.getText());

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        toggledAd.setState(newState);

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<ToggleAdResponse> toggleResult = this.adService.toggleAdvertisement(toggledAd);

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        if(toggleResult.isPresent()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
        } else {
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅ!");
            IO.println();
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void searchAdvertisement(){
<<<<<<< HEAD
//        создание запроса для поиска сообщения
=======
        IO.println("пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ...");
//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
>>>>>>> b38ca39b0a4dc419a51bf4e3eb37c8255d1f5894
        SearchAdRequest searchAd = new SearchAdRequest();

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ:");
        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - ");
        searchAd.setTitle(IO.readln());

        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - ");
        searchAd.setDescription(IO.readln());

        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - ");
        searchAd.setCategory(AdCategoryEnum.fromString(IO.readln()));

        IO.print("пїЅпїЅпїЅпїЅ - ");
        String newPrice = IO.readln();
//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ
        if(!newPrice.isEmpty()) {
            try {
                double inputToDouble = Double.parseDouble(newPrice);
                searchAd.setPrice(Price.ofNumber(inputToDouble));
            } catch(NumberFormatException e){
                searchAd.setPrice(Price.ofText(TextPriceEnum.fromString(newPrice)));
            }
        }
        IO.println("");

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<SearchAdResponse> searchResult = this.adService.searchAdvertisement(searchAd);

//        пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ - пїЅпїЅпїЅпїЅпїЅ
        if(searchResult.isEmpty()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }
//        пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        List<String> lines = searchResult.get().getAds();

        int i = 1;
        for (String line : lines) {
//          пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ "пїЅпїЅпїЅпїЅ:пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ"
            String[] splittedString = line.split("; ");

            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ" + i + ":");
            for (String str : splittedString) {
                String[] strValue = str.split(":", 2);
                if(strValue[0].equals("id")) IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + strValue[1]);
                if(strValue[0].equals("title")) IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + strValue[1]);
                if(strValue[0].equals("description")) IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + strValue[1]);
                if(strValue[0].equals("category")) IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + strValue[1]);
                if(strValue[0].equals("price")) IO.println("пїЅпїЅпїЅпїЅ - " + strValue[1]);
            }
            IO.println();
            i++;
        }

        if(lines.isEmpty()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
        } else {
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
        }
        IO.println();

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void outputAdvertisementsList(){
//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        OutputAdsListRequest outputAds = new OutputAdsListRequest();

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<OutputAdResponse> outputResult = this.adService.outputAdvertisements(outputAds);

//        пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ - пїЅпїЅпїЅпїЅпїЅ
        if(outputResult.isEmpty()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        List<String> lines = outputResult.get().getAds();

        int i = 1;
        for (String line : lines) {
//          пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ "пїЅпїЅпїЅпїЅ:пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ"
            String[] splittedString = line.split("; ");

            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ" + i + ":");
            for (String str : splittedString) {
                String[] strValue = str.split(":", 2);
                if(strValue[0].equals("id")) IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + strValue[1]);
                if(strValue[0].equals("title")) IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + strValue[1]);
                if(strValue[0].equals("description")) IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + strValue[1]);
                if(strValue[0].equals("category")) IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + strValue[1]);
                if(strValue[0].equals("price")) IO.println("пїЅпїЅпїЅпїЅ - " + strValue[1]);
            }
            IO.println();
            i++;
        }

        if(lines.isEmpty()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
        } else {
            IO.println("пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
        }
        IO.println();

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void authUser(){
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ:");

//      пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        AuthRequest authReq = new AuthRequest();
        IO.print("пїЅпїЅпїЅ - ");
        authReq.setName(IO.readln());
        IO.print("пїЅпїЅпїЅпїЅпїЅ - ");
        authReq.setLogin(IO.readln());

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<AuthUserResponse> response = this.userService.authUser(authReq);

//        пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ - пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ
        if(response.isPresent()){
            User user = new User();

            response.ifPresent(s -> {
                user.setName(s.getName());
                user.setLogin(s.getLogin());
                user.setRole(s.getRole());
                user.setId(s.getId());
                user.setState(s.getState());
            });

            currentUser = user;

            IO.println("пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
        } else {
            IO.println("пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            initialCurrentMode = InitialAppModesEnum.UNKNOWN;
            showStartMenu();
        }
    }

    public void registerUser(){
        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ:");

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        RegRequest regReq = new RegRequest();
        IO.print("пїЅпїЅпїЅ - ");
        regReq.setName(IO.readln());
        IO.print("пїЅпїЅпїЅпїЅпїЅ - ");
        regReq.setLogin(IO.readln());

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<RegUserResponse> response = this.userService.regUser(regReq);

//        пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ - пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ
        if(response.isPresent()){
            User user = new User();

            response.ifPresent(s -> {
                user.setName(s.getName());
                user.setLogin(s.getLogin());
                user.setRole(s.getRole());
                user.setId(s.getId());
                user.setState(s.getState());
            });

            currentUser = user;

            IO.println("пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
        } else {
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
        }
    }

    public void toggleUser(){
        IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ: ");

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        FindUserRequest findUser = new FindUserRequest();

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ uuid
        try{
            String userArticle = IO.readln();
            UUID uuid = UUID.fromString(userArticle);
            findUser.setId(uuid);
        } catch (IllegalArgumentException exception){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<FindUserResponse> findResult = this.userService.findUser(findUser);

//        пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ - пїЅпїЅпїЅпїЅпїЅ
        if(findResult.isEmpty()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
            adminCurrentMode = AdminModesEnum.UNKNOWN;
            showAdminMenu();
            return;
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        FindUserResponse foundedUser = findResult.get();

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ
        if(foundedUser.getRole() == UserRoleEnum.ADMIN) {
            IO.println("пїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅ ADMIN!");
            IO.println();
            adminCurrentMode = AdminModesEnum.UNKNOWN;
            showAdminMenu();
            return;
        }

//      пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        ToggleUserRequest toggledUser = new ToggleUserRequest();

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        toggledUser.setId(foundedUser.getId());

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        UserStateEnum newState;
        if(foundedUser.getState().getText().equals("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ")){
            newState = UserStateEnum.IDLE;
        } else {
            newState = UserStateEnum.ACTIVE;
        }

        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + foundedUser.getState().getText());
        IO.println("пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ - " + newState.getText());

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        toggledUser.setState(newState);

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        Optional<ToggleUserResponse> toggleResult = this.userService.toggleUser(toggledUser);

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        if(toggleResult.isPresent()){
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
            IO.println();
        } else {
            IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅ!");
            IO.println();
        }

//        пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        adminCurrentMode = AdminModesEnum.UNKNOWN;
        showAdminMenu();
    }

    public void appLaunch(){
        showStartMenu();

        while (true){
            IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ: ");
            initialCurrentMode = InitialAppModesEnum.fromString(IO.readln());
            if(initialCurrentMode == InitialAppModesEnum.EXIT) return;

            switch (initialCurrentMode){
                case AUTH:
                    authUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                default:
                    IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
                    IO.println();
                    break;
            }

            if(currentUser != null) break;
        }

        if(currentUser.getRole() == UserRoleEnum.ADMIN){
            showAdminMenu();

            while(true){
                IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ: ");
                adminCurrentMode = AdminModesEnum.fromString(IO.readln());
                if(adminCurrentMode == AdminModesEnum.EXIT) return;

                switch (adminCurrentMode){
                    case TOGGLE_USER:
                        toggleUser();
                        break;
                    case TOGGLE_AD:
                        toggleAdvertisement();
                        break;
                    default:
                        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
                        IO.println();
                        break;
                }
            }

        } else {
            showMainMenu();

            while(true){
                IO.print("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ: ");
                currentMode = AppModesEnum.fromString(IO.readln());
                if(currentMode == AppModesEnum.EXIT) return;

                switch (currentMode){
                    case CREATE:
                        createAdvertisement();
                        break;
                    case EDIT:
                        editAdvertisement();
                        break;
                    case TOGGLE:
                        toggleAdvertisement();
                        break;
                    case SEARCH:
                        searchAdvertisement();
                        break;
                    case LIST_OUTPUT:
                        outputAdvertisementsList();
                        break;
                    default:
                        IO.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ!");
                        IO.println();
                        break;
                }
            }
        }
    }
}
