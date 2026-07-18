package repository;

import entity.Advertisement;
import request.*;
import response.*;
import shared.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class AdvertisementRepository implements Repository<Advertisement, FindAdRequest> {
    @Override
    public void save(Advertisement advertisement) throws IOException {
        FileWriter writer = new FileWriter("notes3.txt", Charset.forName("windows-1251"), true);
        String record = "id:" + advertisement.getId() + "; " +
                "authorId:" + advertisement.getAuthorId() + "; " +
                "category:" + advertisement.getCategory().getText() + "; " +
                "title:" + advertisement.getTitle() + "; " +
                "description:" + advertisement.getDescription() + "; " +
                "price:" + (advertisement.getPrice().isText() ? advertisement.getPrice().getStringValue().getText() : advertisement.getPrice().getNumericValue()) + "; " +
                "createdAt:" + advertisement.getDate() + "; " +
                "state:" + advertisement.getState().getText() + "; " +
                "lastChangedBy:" + advertisement.getLastChanger() + "; " +
                "lastStateChangedBy:" + advertisement.getLastChangedState() + "; ";
        writer.write(record);
        writer.append('\n');
        writer.flush();
    }

    @Override
    public Optional<Advertisement> findById(FindAdRequest ad) {
//        получение файла
        Path file = Path.of("notes3.txt");

        try (Stream<String> lines = Files.lines(file, Charset.forName("windows-1251"))) {
//            фильтрация строк файла
            List<String> filteredLines = lines.filter(s -> s.contains("id:" + ad.getId()))
                    .filter(s -> s.contains("authorId:" + (ad.getAuthorId() == null ? "" : ad.getAuthorId()) ))
                    .toList();

//            если нужных строк не найдено - выход
            if(filteredLines.isEmpty()) return Optional.empty();

//            разбиение найденной строки на строки типа "ключ:значение"
            String[] splittedString = filteredLines.getFirst().split("; ");

//            создание объекта для ответа
            Advertisement result = new Advertisement();
            for (String str : splittedString) {
                String[] strValue = str.split(":", 2);
                if(strValue[0].equals("id")) result.setId(UUID.fromString(strValue[1]));
                if(strValue[0].equals("authorId")) result.setAuthorId(UUID.fromString(strValue[1]));
                if(strValue[0].equals("category")) result.setCategory(AdCategoryEnum.fromString(strValue[1]));
                if(strValue[0].equals("title")) result.setTitle(strValue[1]);
                if(strValue[0].equals("description")) result.setDescription(strValue[1]);
                if(strValue[0].equals("price")) {
                    try {
                        double inputToDouble = Double.parseDouble(strValue[1]);
                        result.setPrice(Price.ofNumber(inputToDouble));
                    } catch(NumberFormatException e){
                        result.setPrice(Price.ofText(TextPriceEnum.fromString(strValue[1])));
                    }
                }
                if(strValue[0].equals("createdAt")) result.setDate(Instant.parse(strValue[1]));
                if(strValue[0].equals("state")) result.setState(AdStateEnum.fromString(strValue[1]));
                if(strValue[0].equals("lastChangedBy")) result.setLastChanger(UserRoleEnum.fromString(strValue[1]));
                if(strValue[0].equals("lastStateChangedBy")) result.setLastChangedState(AdStateEnum.fromString(strValue[1]));
            }

            return Optional.of(result);

        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public Optional<CreateAdResponse> createAd(CreateAdRequest ad){
//        создание технических полей
        UUID uuid = UUID.randomUUID();
        Instant createdAt = Instant.now();
        AdStateEnum state = AdStateEnum.ACTIVE;

//        создание объекта нового объявления
        Advertisement newAdEntity = new Advertisement();
        newAdEntity.setId(uuid);
        newAdEntity.setAuthorId(ad.getAuthorId());
        newAdEntity.setCategory(ad.getCategory());
        newAdEntity.setTitle(ad.getTitle());
        newAdEntity.setDescription(ad.getDescription());
        newAdEntity.setPrice(ad.getPrice());
        newAdEntity.setDate(createdAt);
        newAdEntity.setState(state);
        newAdEntity.setLastChanger(null);
        newAdEntity.setLastChangedState(null);

//        сохранение нового объявления в файл
        try {
            save(newAdEntity);
        }
        catch(IOException ex){
            return Optional.empty();
        }

//        создание объекта для ответа
        CreateAdResponse response = new CreateAdResponse();
        response.setId(newAdEntity.getId());
        response.setCategory(newAdEntity.getCategory());
        response.setTitle(newAdEntity.getTitle());
        response.setDescription(newAdEntity.getDescription());
        response.setPrice(newAdEntity.getPrice());

        return Optional.of(response);
    }

    public Optional<FindAdResponse> findAd(FindAdRequest ad) {
//        поиск объявления в файле
        Optional<Advertisement> findResult = this.findById(ad);

//        если объявление не найдено - выход
        if(findResult.isEmpty()) return Optional.empty();

//        объект найденного объявления
        Advertisement foundedAd = findResult.get();

//        создание объекта для ответа
        FindAdResponse response = new FindAdResponse();
        response.setId(foundedAd.getId());
        response.setTitle(foundedAd.getTitle());
        response.setDescription(foundedAd.getDescription());
        response.setCategory(foundedAd.getCategory());
        response.setPrice(foundedAd.getPrice());
        response.setState(foundedAd.getState());
        response.setLastChanger(foundedAd.getLastChanger());

        return Optional.of(response);
    }

    public Optional<EditAdResponse> editAd(EditAdRequest ad){
//        получение файла
        Path path = Paths.get("notes3.txt");

        try {
//            получение строк файла
            List<String> lines = Files.readAllLines(path, Charset.forName("windows-1251"));

//            поиск нужной строки для изменения
            String linesToEdit = "";
            int i = 0;
            for (String line : lines) {
                if(line.contains("id:" + ad.getId())){
                    linesToEdit = line;
                    break;
                } else {
                    i++;
                }
            }

//            разбиение найденной строки на строки типа "ключ:значение"
            String[] splittedString = linesToEdit.split("; ");

//            создание объекта объявления для формирования новой записи
            Advertisement result = new Advertisement();
            for (String str : splittedString) {
                String[] strValue = str.split(":", 2);
                if(strValue[0].equals("id")) result.setId(UUID.fromString(strValue[1]));
                if(strValue[0].equals("authorId")) result.setAuthorId(UUID.fromString(strValue[1]));
                if(strValue[0].equals("category")) result.setCategory(AdCategoryEnum.fromString(strValue[1]));
                if(strValue[0].equals("title")) result.setTitle(strValue[1]);
                if(strValue[0].equals("description")) result.setDescription(strValue[1]);
                if(strValue[0].equals("price")) {
                    try {
                        double inputToDouble = Double.parseDouble(strValue[1]);
                        result.setPrice(Price.ofNumber(inputToDouble));
                    } catch(NumberFormatException e){
                        result.setPrice(Price.ofText(TextPriceEnum.fromString(strValue[1])));
                    }
                }
                if(strValue[0].equals("createdAt")) result.setDate(Instant.parse(strValue[1]));
                if(strValue[0].equals("state")) result.setState(AdStateEnum.fromString(strValue[1]));
                if(strValue[0].equals("lastChangedBy")) result.setLastChanger(UserRoleEnum.fromString(strValue[1]));
                if(strValue[0].equals("lastStateChangedBy")) result.setLastChangedState(AdStateEnum.fromString(strValue[1]));
            }

//            задание объекту объявления изменённые значения
            result.setTitle(ad.getTitle());
            result.setDescription(ad.getDescription());
            result.setCategory(ad.getCategory());
            result.setPrice(ad.getPrice());

//            формирования новой записи
            String record = "id:" + result.getId() + "; " +
                    "authorId:" + result.getAuthorId() + "; " +
                    "category:" + result.getCategory().getText() + "; " +
                    "title:" + result.getTitle() + "; " +
                    "description:" + result.getDescription() + "; " +
                    "price:" + (result.getPrice().isText() ? result.getPrice().getStringValue().getText() : result.getPrice().getNumericValue()) + "; " +
                    "createdAt:" + result.getDate() + "; " +
                    "state:" + result.getState().getText() + "; " +
                    "lastChangedBy:" + result.getLastChanger().getText() + "; " +
                    "lastStateChangedBy:" + result.getLastChangedState().getText() + "; ";

//            внесение изменений в файл
            lines.set(i, record);
            Files.write(path, lines, Charset.forName("windows-1251"));

//            создание объекта для ответа
            EditAdResponse response = new EditAdResponse();
            response.setId(result.getId());
            response.setTitle(result.getTitle());
            response.setDescription(result.getDescription());
            response.setCategory(result.getCategory());
            response.setPrice(result.getPrice());

            return Optional.of(response);
        }
        catch (IOException e) {
            return Optional.empty();
        }
    }

    public Optional<ToggleAdResponse> toggleAd(ToggleAdRequest ad){
//        получение файла
        Path path = Paths.get("notes3.txt");

        try {
//            получение строк файла
            List<String> lines = Files.readAllLines(path, Charset.forName("windows-1251"));

//            поиск нужной строки для изменения
            String linesToEdit = "";
            int i = 0;
            for (String line : lines) {
                if(line.contains("id:" + ad.getId())){
                    linesToEdit = line;
                    break;
                } else {
                    i++;
                }
            }

//            разбиение найденной строки на строки типа "ключ:значение"
            String[] splittedString = linesToEdit.split("; ");

//            создание объекта объявления для формирования новой записи
            Advertisement result = new Advertisement();
            for (String str : splittedString) {
                String[] strValue = str.split(":", 2);
                if(strValue[0].equals("id")) result.setId(UUID.fromString(strValue[1]));
                if(strValue[0].equals("authorId")) result.setAuthorId(UUID.fromString(strValue[1]));
                if(strValue[0].equals("category")) result.setCategory(AdCategoryEnum.fromString(strValue[1]));
                if(strValue[0].equals("title")) result.setTitle(strValue[1]);
                if(strValue[0].equals("description")) result.setDescription(strValue[1]);
                if(strValue[0].equals("price")) {
                    try {
                        double inputToDouble = Double.parseDouble(strValue[1]);
                        result.setPrice(Price.ofNumber(inputToDouble));
                    } catch(NumberFormatException e){
                        result.setPrice(Price.ofText(TextPriceEnum.fromString(strValue[1])));
                    }
                }
                if(strValue[0].equals("createdAt")) result.setDate(Instant.parse(strValue[1]));
                if(strValue[0].equals("state")) result.setState(AdStateEnum.fromString(strValue[1]));
                if(strValue[0].equals("lastChangedBy")) result.setLastChanger(UserRoleEnum.fromString(strValue[1]));
                if(strValue[0].equals("lastStateChangedBy")) result.setLastChangedState(AdStateEnum.fromString(strValue[1]));
            }

//            сохранение предыдущего состояния объявления
            result.setLastChangedState(result.getState());

//            заданию объекту объявления изменённые значения
            result.setState(ad.getState());

//            сохранение роли изменяющего
            result.setLastChanger(ad.getChanger());

//            формирования новой записи
            String record = "id:" + result.getId() + "; " +
                    "authorId:" + result.getAuthorId() + "; " +
                    "category:" + result.getCategory().getText() + "; " +
                    "title:" + result.getTitle() + "; " +
                    "description:" + result.getDescription() + "; " +
                    "price:" + (result.getPrice().isText() ? result.getPrice().getStringValue().getText() : result.getPrice().getNumericValue()) + "; " +
                    "createdAt:" + result.getDate() + "; " +
                    "state:" + result.getState().getText() + "; " +
                    "lastChangedBy:" + result.getLastChanger().getText() + "; " +
                    "lastStateChangedBy:" + result.getLastChangedState().getText() + "; ";

//            внесение изменений в файл
            lines.set(i, record);
            Files.write(path, lines, Charset.forName("windows-1251"));

//            создание объекта для ответа
            ToggleAdResponse response = new ToggleAdResponse();
            response.setId(result.getId());
            response.setTitle(result.getTitle());
            response.setDescription(result.getDescription());
            response.setCategory(result.getCategory());
            response.setPrice(result.getPrice());

            return Optional.of(response);
        }
        catch (IOException e) {
            return Optional.empty();
        }
    }

    public Optional<OutputAdResponse> outputAds(OutputAdsListRequest ad){
//        получение файла
        Path path = Paths.get("notes3.txt");

        try (Stream<String> lines = Files.lines(path, Charset.forName("windows-1251"))) {
//            фильтрация строк файла
            List<String> filteredLines = lines.filter(s -> s.contains("state:Активный")).toList();

//            создание объекта для ответа
            OutputAdResponse response = new OutputAdResponse();
            response.setAds(filteredLines);

            return Optional.of(response);
        }
        catch (IOException e){
            return Optional.empty();
        }
    }

    public Optional<SearchAdResponse> searchAd(SearchAdRequest ad){
        Path path = Paths.get("notes3.txt");

        try (Stream<String> lines = Files.lines(path, Charset.forName("windows-1251"))) {
//            фильтрация результата по активности объявления
            Stream<String> filteredLines = lines.filter(s -> s.contains("state:Активный"));

//            фильтрация результата по названию
            if(!ad.getTitle().isEmpty()) filteredLines = filteredLines.filter(s -> s.contains("title:" + ad.getTitle()));

//            фильтрация результата по описанию
            if(!ad.getDescription().isEmpty()) filteredLines = filteredLines.filter(s -> s.contains("description:" + ad.getDescription()));

//            фильтрация результата по категории
            filteredLines = filteredLines.filter(s -> s.contains("category:" + ad.getCategory().getText()));

//            фильтрация результата по цене
            if(ad.getPrice() != null){
                filteredLines = filteredLines.filter(s -> s.contains("price:" + (ad.getPrice().isText() ? ad.getPrice().getStringValue().getText() : ad.getPrice().getNumericValue())));
            }

//            создание объекта для ответа
            SearchAdResponse response = new SearchAdResponse();
            response.setAds(filteredLines.toList());

            return Optional.of(response);
        }
        catch (IOException e){
            return Optional.empty();
        }
    }
}
