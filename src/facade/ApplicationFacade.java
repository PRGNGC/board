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
//        проверка на заблокированный аккаунт
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("Ваш аккаунт заблокирован, вы можете только просматривать объявления!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        запрос на создание объявления
        CreateAdRequest newAd = new CreateAdRequest();

//        получение данных от пользователя
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
//        определение типа цены
        try {
            double inputToDouble = Double.parseDouble(inputPrice);
            newAd.setPrice(Price.ofNumber(inputToDouble));
        } catch(NumberFormatException e){
            newAd.setPrice(Price.ofText(TextPriceEnum.fromString(inputPrice)));
        }

//        отправка запроса для создания обновления
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
//        проверка на заблокированный аккаунт
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("Ваш аккаунт заблокирован, вы можете только просматривать объявления!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        создание запроса для поиска ообъявления
        FindAdRequest findAd = new FindAdRequest();

        IO.print("Введите артикул объявления: ");

//        проверка на валидный введённый uuid
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

//        поиск объявления
        Optional<FindAdResponse> findResult = this.adService.findAdvertisement(findAd);

//        если объявление не найдено - выход
        if(findResult.isEmpty()){
            IO.println("Объявление не найдено!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        объект найденного объявления
        FindAdResponse foundedAd = findResult.get();

//        запрос на редактирование объявления
        EditAdRequest editedAd = new EditAdRequest();
        editedAd.setTitle(foundedAd.getTitle());
        editedAd.setId(foundedAd.getId());
        editedAd.setDescription(foundedAd.getDescription());
        editedAd.setCategory(foundedAd.getCategory());
        editedAd.setPrice(foundedAd.getPrice());

//        получение новых значений
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
//        определение типа цены
        if(!newPrice.isEmpty()) {
            try {
                double inputToDouble = Double.parseDouble(newPrice);
                editedAd.setPrice(Price.ofNumber(inputToDouble));
            } catch(NumberFormatException e){
                editedAd.setPrice(Price.ofText(TextPriceEnum.fromString(newPrice)));
            }
        }

//        редактирование объявления
        Optional<EditAdResponse> editResult = this.adService.editAdvertisement(editedAd);

//        уведомление о успешности операции
        if(editResult.isPresent()){
            IO.println("Объявление изменено!");
            IO.println();
        } else {
            IO.println("Произошла ошибка, попробуйте ещё раз!");
            IO.println();
        }

//        завершение работы обработчика
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void toggleAdvertisement(){
//        проверка на заблокированный аккаунт
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("Ваш аккаунт заблокирован, вы можете только просматривать объявления!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        IO.print("Введите артикул объявления: ");

//        запрос для поиска объявления
        FindAdRequest findAd = new FindAdRequest();

//        проверка на валидный введённый uuid
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

//        поиск объявления
        Optional<FindAdResponse> findResult = this.adService.findAdvertisement(findAd);

//        если объявление не найдено - выход
        if(findResult.isEmpty()){
            IO.println("Объявление не найдено!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        объект найденного объявления
        FindAdResponse foundedAd = findResult.get();

        if(foundedAd.getLastChanger() == UserRoleEnum.ADMIN && foundedAd.getState() == AdStateEnum.IDLE){
            IO.println("Администратор заблокировал это объявление, вы не можете его активировать!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//      запрос на изменение статуса объявления
        ToggleAdRequest toggledAd = new ToggleAdRequest();
        toggledAd.setState(foundedAd.getState());
        toggledAd.setId(foundedAd.getId());
        toggledAd.setChanger(currentUser.getRole());

//        определение нового статуса
        AdStateEnum newState;
        if(foundedAd.getState().getText().equals("Активный")){
            newState = AdStateEnum.IDLE;
        } else {
            newState = AdStateEnum.ACTIVE;
        }

        IO.println("Старый статус объявления - " + foundedAd.getState().getText());
        IO.println("Новый статус объявления - " + newState.getText());

//        устанновка нового статуса
        toggledAd.setState(newState);

//        изменение статуса объявления
        Optional<ToggleAdResponse> toggleResult = this.adService.toggleAdvertisement(toggledAd);

//        уведомление об успешности операции
        if(toggleResult.isPresent()){
            IO.println("Статус объявления изменён!");
            IO.println();
        } else {
            IO.println("Произошла ошибка, попробуйте ещё раз!");
            IO.println();
        }

//        завершение работы обработчика
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void searchAdvertisement(){
        IO.println("Поиск объявления...");
//        создание запроса для поиска сообщения
        SearchAdRequest searchAd = new SearchAdRequest();

//        получение параметров для поиска
        IO.println("Введите параметры для поиска:");
        IO.print("Название - ");
        searchAd.setTitle(IO.readln());

        IO.print("Описание - ");
        searchAd.setDescription(IO.readln());

        IO.print("Категория - ");
        searchAd.setCategory(AdCategoryEnum.fromString(IO.readln()));

        IO.print("Цена - ");
        String newPrice = IO.readln();
//        определение типа цены
        if(!newPrice.isEmpty()) {
            try {
                double inputToDouble = Double.parseDouble(newPrice);
                searchAd.setPrice(Price.ofNumber(inputToDouble));
            } catch(NumberFormatException e){
                searchAd.setPrice(Price.ofText(TextPriceEnum.fromString(newPrice)));
            }
        }
        IO.println("");

//        отправка запроса для поиска объявления
        Optional<SearchAdResponse> searchResult = this.adService.searchAdvertisement(searchAd);

//        если произошла ошибка - выход
        if(searchResult.isEmpty()){
            IO.println("Произошла ошибка!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }
//        объект найденного объявления
        List<String> lines = searchResult.get().getAds();

        int i = 1;
        for (String line : lines) {
//          разбиение найденной строки на строки типа "ключ:значение"
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

//        завершение работы обработчика
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void outputAdvertisementsList(){
//        создание запроса для получения объявлений
        OutputAdsListRequest outputAds = new OutputAdsListRequest();

//        отправка запроса для получения объявлений
        Optional<OutputAdResponse> outputResult = this.adService.outputAdvertisements(outputAds);

//        если произошла ошибка - выход
        if(outputResult.isEmpty()){
            IO.println("Произошла ошибка!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        объект найденного объявления
        List<String> lines = outputResult.get().getAds();

        int i = 1;
        for (String line : lines) {
//          разбиение найденной строки на подстроки типа "ключ:значение"
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

//        завершение работы обработчика
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void authUser(){
        IO.println("Введите данные:");

//      получение данных от пользователей
        AuthRequest authReq = new AuthRequest();
        IO.print("Имя - ");
        authReq.setName(IO.readln());
        IO.print("Логин - ");
        authReq.setLogin(IO.readln());

//        создание запроса для авторизации пользователя
        Optional<AuthUserResponse> response = this.userService.authUser(authReq);

//        если пользователь найден - авторизация, иначе выход
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

//        получение данных от пользователя
        RegRequest regReq = new RegRequest();
        IO.print("Имя - ");
        regReq.setName(IO.readln());
        IO.print("Логин - ");
        regReq.setLogin(IO.readln());

//        создание запроса для регистрации пользователя
        Optional<RegUserResponse> response = this.userService.regUser(regReq);

//        если пользователь создан - авторизация, иначе выход
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

//        создание запроса для поиска пользователя
        FindUserRequest findUser = new FindUserRequest();

//        проверка на валидный введённый uuid
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

//        поиск пользователя
        Optional<FindUserResponse> findResult = this.userService.findUser(findUser);

//        если пользователь не найден - выход
        if(findResult.isEmpty()){
            IO.println("Пользователь не найден!");
            IO.println();
            adminCurrentMode = AdminModesEnum.UNKNOWN;
            showAdminMenu();
            return;
        }

//        объект найденного объявления
        FindUserResponse foundedUser = findResult.get();

//        проверка на попытку изменить статус пользователя с ролью админ
        if(foundedUser.getRole() == UserRoleEnum.ADMIN) {
            IO.println("Вы не можете изменять статус пользователя с ролью ADMIN!");
            IO.println();
            adminCurrentMode = AdminModesEnum.UNKNOWN;
            showAdminMenu();
            return;
        }

//      запрос на изменение статуса объявления
        ToggleUserRequest toggledUser = new ToggleUserRequest();

//        задание значений по умолчанию для запроса
        toggledUser.setId(foundedUser.getId());

//        определение нового статуса
        UserStateEnum newState;
        if(foundedUser.getState().getText().equals("Активный")){
            newState = UserStateEnum.IDLE;
        } else {
            newState = UserStateEnum.ACTIVE;
        }

        IO.println("Старый статус пользователя - " + foundedUser.getState().getText());
        IO.println("Новый статус пользователя - " + newState.getText());

//        установка нового статуса пользователя
        toggledUser.setState(newState);

//        изменение статуса объявления
        Optional<ToggleUserResponse> toggleResult = this.userService.toggleUser(toggledUser);

//        уведомление об успешности операции
        if(toggleResult.isPresent()){
            IO.println("Статус пользователя изменён!");
            IO.println();
        } else {
            IO.println("Произошла ошибка, попробуйте ещё раз!");
            IO.println();
        }

//        завершение работы обработчика
        adminCurrentMode = AdminModesEnum.UNKNOWN;
        showAdminMenu();
    }

    public void appLaunch(){
        showStartMenu();

        while (true){
            IO.print("Введите число: ");
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
                    IO.println("Неизвестная команда!");
                    IO.println();
                    break;
            }

            if(currentUser != null) break;
        }

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
