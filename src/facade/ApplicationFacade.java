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
        IO.println("*���������������� ������: ");
        IO.println("�������� ��� ��������:");
        IO.println("�������� ������ ������������ - 1");
        IO.println("�������� ������ ���������� - 2");
        IO.println("��������� ������ - ex");
        IO.println();
    }

    public void showStartMenu(){
        IO.println("�������� ��� ��������:");
        IO.println("�������������� - 1");
        IO.println("������������������ - 2");
        IO.println("��������� ������ - ex");
        IO.println();
    }

    public void showMainMenu(){
        IO.println("�������� ��� ��������:");
        IO.println("������� ���������� - 1");
        IO.println("������������� ���������� - 2");
        IO.println("�������� ������ ���������� - 3");
        IO.println("����� ���������� - 4");
        IO.println("���������� ������ ���������� - 5");
        IO.println("��������� ������ - ex");
        IO.println();
    }

    public void createAdvertisement(){
//        �������� �� ��������������� �������
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("��� ������� ������������, �� ������ ������ ������������� ����������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        ������ �� �������� ����������
        CreateAdRequest newAd = new CreateAdRequest();

//        ��������� ������ �� ������������
        newAd.setAuthorId(currentUser.getId());
        IO.println("��������� ����:");

        IO.print("�������� - ");
        newAd.setTitle(IO.readln());

        IO.print("�������� - ");
        newAd.setDescription(IO.readln());

        IO.print("��������� - ");
        newAd.setCategory(AdCategoryEnum.fromString(IO.readln()));

        IO.print("���� - ");
        String inputPrice = IO.readln();
//        ����������� ���� ����
        try {
            double inputToDouble = Double.parseDouble(inputPrice);
            newAd.setPrice(Price.ofNumber(inputToDouble));
        } catch(NumberFormatException e){
            newAd.setPrice(Price.ofText(TextPriceEnum.fromString(inputPrice)));
        }

//        �������� ������� ��� �������� ����������
        Optional<CreateAdResponse> result = this.adService.createAdvertisement(newAd);

        if(result.isPresent()){
            IO.println("���������� �������!");
            IO.println();
        } else {
            IO.println("��������� ������, ���������� ��� ���!");
            IO.println();
        }

        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void editAdvertisement(){
//        �������� �� ��������������� �������
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("��� ������� ������������, �� ������ ������ ������������� ����������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        �������� ������� ��� ������ �����������
        FindAdRequest findAd = new FindAdRequest();

        IO.print("������� ������� ����������: ");

//        �������� �� �������� �������� uuid
        try{
            String adArticle = IO.readln();
            UUID uuid = UUID.fromString(adArticle);
            findAd.setId(uuid);
        } catch (IllegalArgumentException exception){
            IO.println("���������� �� �������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        findAd.setAuthorId(currentUser.getId());

//        ����� ����������
        Optional<FindAdResponse> findResult = this.adService.findAdvertisement(findAd);

//        ���� ���������� �� ������� - �����
        if(findResult.isEmpty()){
            IO.println("���������� �� �������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        ������ ���������� ����������
        FindAdResponse foundedAd = findResult.get();

//        ������ �� �������������� ����������
        EditAdRequest editedAd = new EditAdRequest();
        editedAd.setTitle(foundedAd.getTitle());
        editedAd.setId(foundedAd.getId());
        editedAd.setDescription(foundedAd.getDescription());
        editedAd.setCategory(foundedAd.getCategory());
        editedAd.setPrice(foundedAd.getPrice());

//        ��������� ����� ��������
        IO.println("��������� ����: (*���������� ��������*)");
        IO.print("��������(" + foundedAd.getTitle() + ") - ");
        String newTitle = IO.readln();
        if(!newTitle.isEmpty()) editedAd.setTitle(newTitle);

        IO.print("��������(" + foundedAd.getDescription() + ") - ");
        String newDesc = IO.readln();
        if(!newDesc.isEmpty()) editedAd.setDescription(newDesc);

        IO.print("���������(" + foundedAd.getCategory().getText() + ") - ");
        String newCategory = IO.readln();
        if(!newCategory.isEmpty()) editedAd.setCategory(AdCategoryEnum.fromString(newCategory));

        IO.print("����(" + (foundedAd.getPrice().isText() ? foundedAd.getPrice().getStringValue().getText() : foundedAd.getPrice().getNumericValue()) + ") - ");
        String newPrice = IO.readln();
//        ����������� ���� ����
        if(!newPrice.isEmpty()) {
            try {
                double inputToDouble = Double.parseDouble(newPrice);
                editedAd.setPrice(Price.ofNumber(inputToDouble));
            } catch(NumberFormatException e){
                editedAd.setPrice(Price.ofText(TextPriceEnum.fromString(newPrice)));
            }
        }

//        �������������� ����������
        Optional<EditAdResponse> editResult = this.adService.editAdvertisement(editedAd);

//        ����������� � ���������� ��������
        if(editResult.isPresent()){
            IO.println("���������� ��������!");
            IO.println();
        } else {
            IO.println("��������� ������, ���������� ��� ���!");
            IO.println();
        }

//        ���������� ������ �����������
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void toggleAdvertisement(){
//        �������� �� ��������������� �������
        if(currentUser.getState() == UserStateEnum.IDLE) {
            IO.println("��� ������� ������������, �� ������ ������ ������������� ����������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        IO.print("������� ������� ����������: ");

//        ������ ��� ������ ����������
        FindAdRequest findAd = new FindAdRequest();

//        �������� �� �������� �������� uuid
        try{
            String adArticle = IO.readln();
            UUID uuid = UUID.fromString(adArticle);
            findAd.setId(uuid);
        } catch (IllegalArgumentException exception){
            IO.println("���������� �� �������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

        if(currentUser.getRole() != UserRoleEnum.ADMIN) findAd.setAuthorId(currentUser.getId());

//        ����� ����������
        Optional<FindAdResponse> findResult = this.adService.findAdvertisement(findAd);

//        ���� ���������� �� ������� - �����
        if(findResult.isEmpty()){
            IO.println("���������� �� �������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        ������ ���������� ����������
        FindAdResponse foundedAd = findResult.get();

        if(foundedAd.getLastChanger() == UserRoleEnum.ADMIN && foundedAd.getState() == AdStateEnum.IDLE){
            IO.println("������������� ������������ ��� ����������, �� �� ������ ��� ������������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//      ������ �� ��������� ������� ����������
        ToggleAdRequest toggledAd = new ToggleAdRequest();
        toggledAd.setState(foundedAd.getState());
        toggledAd.setId(foundedAd.getId());
        toggledAd.setChanger(currentUser.getRole());

//        ����������� ������ �������
        AdStateEnum newState;
        if(foundedAd.getState().getText().equals("��������")){
            newState = AdStateEnum.IDLE;
        } else {
            newState = AdStateEnum.ACTIVE;
        }

        IO.println("������ ������ ���������� - " + foundedAd.getState().getText());
        IO.println("����� ������ ���������� - " + newState.getText());

//        ���������� ������ �������
        toggledAd.setState(newState);

//        ��������� ������� ����������
        Optional<ToggleAdResponse> toggleResult = this.adService.toggleAdvertisement(toggledAd);

//        ����������� �� ���������� ��������
        if(toggleResult.isPresent()){
            IO.println("������ ���������� �������!");
            IO.println();
        } else {
            IO.println("��������� ������, ���������� ��� ���!");
            IO.println();
        }

//        ���������� ������ �����������
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void searchAdvertisement(){
        IO.println("����� ����������...");
//        �������� ������� ��� ������ ���������
        SearchAdRequest searchAd = new SearchAdRequest();

//        ��������� ���������� ��� ������
        IO.println("������� ��������� ��� ������:");
        IO.print("�������� - ");
        searchAd.setTitle(IO.readln());

        IO.print("�������� - ");
        searchAd.setDescription(IO.readln());

        IO.print("��������� - ");
        searchAd.setCategory(AdCategoryEnum.fromString(IO.readln()));

        IO.print("���� - ");
        String newPrice = IO.readln();
//        ����������� ���� ����
        if(!newPrice.isEmpty()) {
            try {
                double inputToDouble = Double.parseDouble(newPrice);
                searchAd.setPrice(Price.ofNumber(inputToDouble));
            } catch(NumberFormatException e){
                searchAd.setPrice(Price.ofText(TextPriceEnum.fromString(newPrice)));
            }
        }
        IO.println("");

//        �������� ������� ��� ������ ����������
        Optional<SearchAdResponse> searchResult = this.adService.searchAdvertisement(searchAd);

//        ���� ��������� ������ - �����
        if(searchResult.isEmpty()){
            IO.println("��������� ������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }
//        ������ ���������� ����������
        List<String> lines = searchResult.get().getAds();

        int i = 1;
        for (String line : lines) {
//          ��������� ��������� ������ �� ������ ���� "����:��������"
            String[] splittedString = line.split("; ");

            IO.println("���������� �" + i + ":");
            for (String str : splittedString) {
                String[] strValue = str.split(":", 2);
                if(strValue[0].equals("id")) IO.println("������� - " + strValue[1]);
                if(strValue[0].equals("title")) IO.println("�������� - " + strValue[1]);
                if(strValue[0].equals("description")) IO.println("�������� - " + strValue[1]);
                if(strValue[0].equals("category")) IO.println("��������� - " + strValue[1]);
                if(strValue[0].equals("price")) IO.println("���� - " + strValue[1]);
            }
            IO.println();
            i++;
        }

        if(lines.isEmpty()){
            IO.println("������ �� �������!");
        } else {
            IO.println("��������� ���������� ��������!");
        }
        IO.println();

//        ���������� ������ �����������
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void outputAdvertisementsList(){
//        �������� ������� ��� ��������� ����������
        OutputAdsListRequest outputAds = new OutputAdsListRequest();

//        �������� ������� ��� ��������� ����������
        Optional<OutputAdResponse> outputResult = this.adService.outputAdvertisements(outputAds);

//        ���� ��������� ������ - �����
        if(outputResult.isEmpty()){
            IO.println("��������� ������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        ������ ���������� ����������
        List<String> lines = outputResult.get().getAds();

        int i = 1;
        for (String line : lines) {
//          ��������� ��������� ������ �� ��������� ���� "����:��������"
            String[] splittedString = line.split("; ");

            IO.println("���������� �" + i + ":");
            for (String str : splittedString) {
                String[] strValue = str.split(":", 2);
                if(strValue[0].equals("id")) IO.println("������� - " + strValue[1]);
                if(strValue[0].equals("title")) IO.println("�������� - " + strValue[1]);
                if(strValue[0].equals("description")) IO.println("�������� - " + strValue[1]);
                if(strValue[0].equals("category")) IO.println("��������� - " + strValue[1]);
                if(strValue[0].equals("price")) IO.println("���� - " + strValue[1]);
            }
            IO.println();
            i++;
        }

        if(lines.isEmpty()){
            IO.println("������ �� �������!");
        } else {
            IO.println("��� ���������� ��������!");
        }
        IO.println();

//        ���������� ������ �����������
        currentMode = AppModesEnum.UNKNOWN;
        showMainMenu();
    }

    public void authUser(){
        IO.println("������� ������:");

//      ��������� ������ �� �������������
        AuthRequest authReq = new AuthRequest();
        IO.print("��� - ");
        authReq.setName(IO.readln());
        IO.print("����� - ");
        authReq.setLogin(IO.readln());

//        �������� ������� ��� ����������� ������������
        Optional<AuthUserResponse> response = this.userService.authUser(authReq);

//        ���� ������������ ������ - �����������, ����� �����
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

            IO.println("�� ������������!");
            IO.println();
        } else {
            IO.println("����� ������������ �� ���������!");
            IO.println();
            initialCurrentMode = InitialAppModesEnum.UNKNOWN;
            showStartMenu();
        }
    }

    public void registerUser(){
        IO.println("������� ������:");

//        ��������� ������ �� ������������
        RegRequest regReq = new RegRequest();
        IO.print("��� - ");
        regReq.setName(IO.readln());
        IO.print("����� - ");
        regReq.setLogin(IO.readln());

//        �������� ������� ��� ����������� ������������
        Optional<RegUserResponse> response = this.userService.regUser(regReq);

//        ���� ������������ ������ - �����������, ����� �����
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

            IO.println("�� ���������������� � ������������!");
            IO.println();
        } else {
            IO.println("��������� ������, ���������� ��� ���!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
        }
    }

    public void toggleUser(){
        IO.print("������� ������� ������������: ");

//        �������� ������� ��� ������ ������������
        FindUserRequest findUser = new FindUserRequest();

//        �������� �� �������� �������� uuid
        try{
            String userArticle = IO.readln();
            UUID uuid = UUID.fromString(userArticle);
            findUser.setId(uuid);
        } catch (IllegalArgumentException exception){
            IO.println("������������ �� ������!");
            IO.println();
            currentMode = AppModesEnum.UNKNOWN;
            showMainMenu();
            return;
        }

//        ����� ������������
        Optional<FindUserResponse> findResult = this.userService.findUser(findUser);

//        ���� ������������ �� ������ - �����
        if(findResult.isEmpty()){
            IO.println("������������ �� ������!");
            IO.println();
            adminCurrentMode = AdminModesEnum.UNKNOWN;
            showAdminMenu();
            return;
        }

//        ������ ���������� ����������
        FindUserResponse foundedUser = findResult.get();

//        �������� �� ������� �������� ������ ������������ � ����� �����
        if(foundedUser.getRole() == UserRoleEnum.ADMIN) {
            IO.println("�� �� ������ �������� ������ ������������ � ����� ADMIN!");
            IO.println();
            adminCurrentMode = AdminModesEnum.UNKNOWN;
            showAdminMenu();
            return;
        }

//      ������ �� ��������� ������� ����������
        ToggleUserRequest toggledUser = new ToggleUserRequest();

//        ������� �������� �� ��������� ��� �������
        toggledUser.setId(foundedUser.getId());

//        ����������� ������ �������
        UserStateEnum newState;
        if(foundedUser.getState().getText().equals("��������")){
            newState = UserStateEnum.IDLE;
        } else {
            newState = UserStateEnum.ACTIVE;
        }

        IO.println("������ ������ ������������ - " + foundedUser.getState().getText());
        IO.println("����� ������ ������������ - " + newState.getText());

//        ��������� ������ ������� ������������
        toggledUser.setState(newState);

//        ��������� ������� ����������
        Optional<ToggleUserResponse> toggleResult = this.userService.toggleUser(toggledUser);

//        ����������� �� ���������� ��������
        if(toggleResult.isPresent()){
            IO.println("������ ������������ �������!");
            IO.println();
        } else {
            IO.println("��������� ������, ���������� ��� ���!");
            IO.println();
        }

//        ���������� ������ �����������
        adminCurrentMode = AdminModesEnum.UNKNOWN;
        showAdminMenu();
    }

    public void appLaunch(){
        showStartMenu();

        while (true){
            IO.print("������� �����: ");
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
                    IO.println("����������� �������!");
                    IO.println();
                    break;
            }

            if(currentUser != null) break;
        }

        if(currentUser.getRole() == UserRoleEnum.ADMIN){
            showAdminMenu();

            while(true){
                IO.print("������� �����: ");
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
                        IO.println("����������� �������!");
                        IO.println();
                        break;
                }
            }

        } else {
            showMainMenu();

            while(true){
                IO.print("������� �����: ");
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
                        IO.println("����������� �������!");
                        IO.println();
                        break;
                }
            }
        }
    }
}
