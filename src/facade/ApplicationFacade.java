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
        IO.println("*Функциональность админа: ");
        IO.println("Выберите тип операции:");
        IO.println("Изменить статус пользователя - 1");
        IO.println("Изменить статус объявления - 2");
        IO.println("Завершить работу - ex");
        IO.println();
    }

    public void showStartMenu(){
        IO.println("Выберите тип операции:");
        IO.println("Авторизоваться - 1");
        IO.println("Зарегистрироваться - 2");
        IO.println("Завершить работу - ex");
        IO.println();
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
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("Ваш аккаунт заблокирован, вы можете только просматривать объявления!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        CreateAdRequest newAd = new CreateAdRequest();

        newAd.setAuthorId(currentUser.getId());
        IO.println("Заполните поля:");

        IO.print("Название - ");
        newAd.setTitle(IO.readln());

        IO.print("Описание - ");
        newAd.setDescription(IO.readln());

        IO.print("Категория - ");
        newAd.setCategory(AdCategoryEnum.fromString(IO.readln()));

        IO.print("Цена - ");
        String inputPrice = IO.readln();
        try {
            double inputToDouble = Double.parseDouble(inputPrice);
            newAd.setPrice(Price.ofNumber(inputToDouble));
        } catch(NumberFormatException e){
            newAd.setPrice(Price.ofText(TextPriceEnum.fromString(inputPrice)));
        }

        Optional<CreateAdResponse> result = this.adService.createAdvertisement(newAd);

        if(result.isPresent()){
            IO.println("Объявление создано!");
            IO.println();
        } else {
            IO.println("Произошла ошибка, попробуйте ещё раз!");
            IO.println();
        }

        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void editAdvertisement(){
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("Ваш аккаунт заблокирован, вы можете только просматривать объявления!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        FindAdRequest findAd = new FindAdRequest();

        IO.print("Введите артикул объявления: ");

        try{
            String adArticle = IO.readln();
            UUID uuid = UUID.fromString(adArticle);
            findAd.setId(uuid);
        } catch (IllegalArgumentException exception){
            IO.println("Объявление не найдено!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        findAd.setAuthorId(currentUser.getId());

        Optional<FindAdResponse> findResult = this.adService.findAdvertisement(findAd);

        if(findResult.isEmpty()){
            IO.println("Объявление не найдено!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        FindAdResponse foundedAd = findResult.get();

        EditAdRequest editedAd = new EditAdRequest();
        editedAd.setTitle(foundedAd.getTitle());
        editedAd.setId(foundedAd.getId());
        editedAd.setDescription(foundedAd.getDescription());
        editedAd.setCategory(foundedAd.getCategory());
        editedAd.setPrice(foundedAd.getPrice());

        IO.println("Заполните поля: (*предыдущее значение*)");
        IO.print("Название(" + foundedAd.getTitle() + ") - ");
        String newTitle = IO.readln();
        if(!newTitle.isEmpty()) editedAd.setTitle(newTitle);

        IO.print("Описание(" + foundedAd.getDescription() + ") - ");
        String newDesc = IO.readln();
        if(!newDesc.isEmpty()) editedAd.setDescription(newDesc);

        IO.print("Категория(" + foundedAd.getCategory().getText() + ") - ");
        String newCategory = IO.readln();
        if(!newCategory.isEmpty()) editedAd.setCategory(AdCategoryEnum.fromString(newCategory));

        IO.print("Цена(" + (foundedAd.getPrice().isText() ? foundedAd.getPrice().getStringValue().getText() : foundedAd.getPrice().getNumericValue()) + ") - ");
        String newPrice = IO.readln();
        if(!newPrice.isEmpty()) {
            try {
                double inputToDouble = Double.parseDouble(newPrice);
                editedAd.setPrice(Price.ofNumber(inputToDouble));
            } catch(NumberFormatException e){
                editedAd.setPrice(Price.ofText(TextPriceEnum.fromString(newPrice)));
            }
        }

        Optional<EditAdResponse> editResult = this.adService.editAdvertisement(editedAd);

        if(editResult.isPresent()){
            IO.println("Объявление изменено!");
            IO.println();
        } else {
            IO.println("Произошла ошибка, попробуйте ещё раз!");
            IO.println();
        }

        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void toggleAdvertisement(){
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("Ваш аккаунт заблокирован, вы можете только просматривать объявления!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        IO.print("Введите артикул объявления: ");

        FindAdRequest findAd = new FindAdRequest();

        try{
            String adArticle = IO.readln();
            UUID uuid = UUID.fromString(adArticle);
            findAd.setId(uuid);
        } catch (IllegalArgumentException exception){
            IO.println("Объявление не найдено!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        if(currentUser.getRole() != UserRoleEnum.ADMIN) findAd.setAuthorId(currentUser.getId());

        Optional<FindAdResponse> findResult = this.adService.findAdvertisement(findAd);

        if(findResult.isEmpty()){
            IO.println("Объявление не найдено!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        FindAdResponse foundedAd = findResult.get();

        if(foundedAd.getLastChanger() == UserRoleEnum.ADMIN && foundedAd.getState() == AdStateEnum.IDLE){
            IO.println("Администратор заблокировал это объявление, вы не можете его активировать!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        ToggleAdRequest toggledAd = new ToggleAdRequest();
        toggledAd.setState(foundedAd.getState());
        toggledAd.setId(foundedAd.getId());
        toggledAd.setChanger(currentUser.getRole());

        AdStateEnum newState;
        if(foundedAd.getState().getText().equals("Активный")){
            newState = AdStateEnum.IDLE;
        } else {
            newState = AdStateEnum.ACTIVE;
        }

        IO.println("Старый статус объявления - " + foundedAd.getState().getText());
        IO.println("Новый статус объявления - " + newState.getText());

        toggledAd.setState(newState);

        Optional<ToggleAdResponse> toggleResult = this.adService.toggleAdvertisement(toggledAd);

        if(toggleResult.isPresent()){
            IO.println("Статус объявления изменён!");
            IO.println();
        } else {
            IO.println("Произошла ошибка, попробуйте ещё раз!");
            IO.println();
        }

        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void searchAdvertisement(){
        SearchAdRequest searchAd = new SearchAdRequest();

        IO.println("Введите параметры для поиска:");
        IO.print("Название - ");
        searchAd.setTitle(IO.readln());

        IO.print("Описание - ");
        searchAd.setDescription(IO.readln());

        IO.print("Категория - ");
        searchAd.setCategory(AdCategoryEnum.fromString(IO.readln()));

        IO.print("Цена - ");
        String newPrice = IO.readln();
        if(!newPrice.isEmpty()) {
            try {
                double inputToDouble = Double.parseDouble(newPrice);
                searchAd.setPrice(Price.ofNumber(inputToDouble));
            } catch(NumberFormatException e){
                searchAd.setPrice(Price.ofText(TextPriceEnum.fromString(newPrice)));
            }
        }
        IO.println("");

        Optional<SearchAdResponse> searchResult = this.adService.searchAdvertisement(searchAd);

        if(searchResult.isEmpty()){
            IO.println("Ничего не найдено!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }
        List<String> lines = searchResult.get().getAds();

        int i = 1;
        for (String line : lines) {
            String[] splittedString = line.split("; ");

            IO.println("Объявление №" + i + ":");
            for (String str : splittedString) {
                String[] strValue = str.split(":", 2);
                if(strValue[0].equals("id")) IO.println("Артикул - " + strValue[1]);
                if(strValue[0].equals("title")) IO.println("Название - " + strValue[1]);
                if(strValue[0].equals("description")) IO.println("Описание - " + strValue[1]);
                if(strValue[0].equals("category")) IO.println("Категория - " + strValue[1]);
                if(strValue[0].equals("price")) IO.println("Цена - " + strValue[1]);
            }
            IO.println();
            i++;
        }

        if(lines.isEmpty()){
            IO.println("Ничего не найдено!");
        } else {
            IO.println("Найденные объявления выведены!");
        }
        IO.println();

        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void outputAdvertisementsList(){
        OutputAdsListRequest outputAds = new OutputAdsListRequest();

        Optional<OutputAdResponse> outputResult = this.adService.outputAdvertisements(outputAds);

        if(outputResult.isEmpty()){
            IO.println("Ничего не найдено!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        List<String> lines = outputResult.get().getAds();

        int i = 1;
        for (String line : lines) {
            String[] splittedString = line.split("; ");

            IO.println("Объявление №" + i + ":");
            for (String str : splittedString) {
                String[] strValue = str.split(":", 2);
                if(strValue[0].equals("id")) IO.println("Артикул - " + strValue[1]);
                if(strValue[0].equals("title")) IO.println("Название - " + strValue[1]);
                if(strValue[0].equals("description")) IO.println("Описание - " + strValue[1]);
                if(strValue[0].equals("category")) IO.println("Категория - " + strValue[1]);
                if(strValue[0].equals("price")) IO.println("Цена - " + strValue[1]);
            }
            IO.println();
            i++;
        }

        if(lines.isEmpty()){
            IO.println("Ничего не найдено!");
        } else {
            IO.println("Все объявления выведены!");
        }
        IO.println();

        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void authUser(){
        IO.println("Введите данные:");

        AuthRequest authReq = new AuthRequest();
        IO.print("Имя - ");
        authReq.setName(IO.readln());
        IO.print("Логин - ");
        authReq.setLogin(IO.readln());

        Optional<AuthUserResponse> response = this.userService.authUser(authReq);

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

            IO.println("Вы авторизованы!");
            IO.println();
        } else {
            IO.println("Такой пользователь не обнаружен!");
            IO.println();
            initialCurrentMode = InitialAppModesEnum.UNKNOWN;
            showStartMenu();
        }
    }

    public void registerUser(){
        IO.println("Введите данные:");

        RegRequest regReq = new RegRequest();
        IO.print("Имя - ");
        regReq.setName(IO.readln());
        IO.print("Логин - ");
        regReq.setLogin(IO.readln());

        Optional<RegUserResponse> response = this.userService.regUser(regReq);

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

            IO.println("Вы зарегистрированы и авторизованы!");
            IO.println();
        } else {
            IO.println("Произошла ошибка, попробуйте ещё раз!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
        }
    }

    public void toggleUser(){
        IO.print("Введите артикул пользователя: ");

        FindUserRequest findUser = new FindUserRequest();

        try{
            String userArticle = IO.readln();
            UUID uuid = UUID.fromString(userArticle);
            findUser.setId(uuid);
        } catch (IllegalArgumentException exception){
            IO.println("Пользователь не найден!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        Optional<FindUserResponse> findResult = this.userService.findUser(findUser);

        if(findResult.isEmpty()){
            IO.println("Пользователь не найден!");
            IO.println();
            adminCurrentMode = AdminModesEnum.UNKNOWN;
            showAdminMenu();
            return;
        }

        FindUserResponse foundedUser = findResult.get();

        if(foundedUser.getRole() == UserRoleEnum.ADMIN) {
            IO.println("Вы не можете изменять статус пользователя с ролью ADMIN!");
            IO.println();
            adminCurrentMode = AdminModesEnum.UNKNOWN;
            showAdminMenu();
            return;
        }

        ToggleUserRequest toggledUser = new ToggleUserRequest();

        toggledUser.setId(foundedUser.getId());

        UserStateEnum newState;
        if(foundedUser.getState().getText().equals("Активный")){
            newState = UserStateEnum.IDLE;
        } else {
            newState = UserStateEnum.ACTIVE;
        }

        IO.println("Старый статус пользователя - " + foundedUser.getState().getText());
        IO.println("Новый статус пользователя - " + newState.getText());

        toggledUser.setState(newState);

        Optional<ToggleUserResponse> toggleResult = this.userService.toggleUser(toggledUser);

        if(toggleResult.isPresent()){
            IO.println("Статус пользователя изменён!");
            IO.println();
        } else {
            IO.println("Произошла ошибка, попробуйте ещё раз!");
            IO.println();
        }

        adminCurrentMode = AdminModesEnum.UNKNOWN;
        showAdminMenu();
    }

    public void appLaunch(){
        showStartMenu();

        do {
            IO.print("Введите число: ");
            initialCurrentMode = InitialAppModesEnum.fromString(IO.readln());
            if (initialCurrentMode == InitialAppModesEnum.EXIT) return;

            switch (initialCurrentMode) {
                case AUTH:
                    authUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                default:
                    IO.println("Неизвестная команда!");
                    IO.println();
                    break;
            }

        } while (currentUser == null);

        if(currentUser.getRole() == UserRoleEnum.ADMIN){
            showAdminMenu();

            while(true){
                IO.print("Введите число: ");
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
                        IO.println("Неизвестная команда!");
                        IO.println();
                        break;
                }
            }

        } else {
            showMainMenu();

            while(true){
                IO.print("Введите число: ");
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
                        IO.println("Неизвестная команда!");
                        IO.println();
                        break;
                }
            }
        }
    }
}
