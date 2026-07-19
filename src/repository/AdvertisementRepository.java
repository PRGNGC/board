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
        Path file = Path.of("notes3.txt");

        try (Stream<String> lines = Files.lines(file, Charset.forName("windows-1251"))) {
            List<String> filteredLines = lines.filter(s -> s.contains("id:" + ad.getId()))
                    .filter(s -> s.contains("authorId:" + (ad.getAuthorId() == null ? "" : ad.getAuthorId()) ))
                    .toList();

            if(filteredLines.isEmpty()) return Optional.empty();

            String[] splittedString = filteredLines.getFirst().split("; ");

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
        UUID uuid = UUID.randomUUID();
        Instant createdAt = Instant.now();
        AdStateEnum state = AdStateEnum.ACTIVE;

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

        try {
            save(newAdEntity);
        }
        catch(IOException ex){
            return Optional.empty();
        }

        CreateAdResponse response = new CreateAdResponse();
        response.setId(newAdEntity.getId());
        response.setCategory(newAdEntity.getCategory());
        response.setTitle(newAdEntity.getTitle());
        response.setDescription(newAdEntity.getDescription());
        response.setPrice(newAdEntity.getPrice());

        return Optional.of(response);
    }

    public Optional<FindAdResponse> findAd(FindAdRequest ad) {
        Optional<Advertisement> findResult = this.findById(ad);

        if(findResult.isEmpty()) return Optional.empty();

        Advertisement foundedAd = findResult.get();

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
        Path path = Paths.get("notes3.txt");

        try {
            List<String> lines = Files.readAllLines(path, Charset.forName("windows-1251"));

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

            String[] splittedString = linesToEdit.split("; ");

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

            result.setTitle(ad.getTitle());
            result.setDescription(ad.getDescription());
            result.setCategory(ad.getCategory());
            result.setPrice(ad.getPrice());

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

            lines.set(i, record);
            Files.write(path, lines, Charset.forName("windows-1251"));

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
        Path path = Paths.get("notes3.txt");

        try {
            List<String> lines = Files.readAllLines(path, Charset.forName("windows-1251"));

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

            String[] splittedString = linesToEdit.split("; ");

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

            result.setLastChangedState(result.getState());

            result.setState(ad.getState());

            result.setLastChanger(ad.getChanger());

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

            lines.set(i, record);
            Files.write(path, lines, Charset.forName("windows-1251"));

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
        Path path = Paths.get("notes3.txt");

        try (Stream<String> lines = Files.lines(path, Charset.forName("windows-1251"))) {
            List<String> filteredLines = lines.filter(s -> s.contains("state:Активный")).toList();

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
            Stream<String> filteredLines = lines.filter(s -> s.contains("state:Активный"));

            if(!ad.getTitle().isEmpty()) filteredLines = filteredLines.filter(s -> s.contains("title:" + ad.getTitle()));

            if(!ad.getDescription().isEmpty()) filteredLines = filteredLines.filter(s -> s.contains("description:" + ad.getDescription()));

            filteredLines = filteredLines.filter(s -> s.contains("category:" + ad.getCategory().getText()));

            if(ad.getPrice() != null){
                filteredLines = filteredLines.filter(s -> s.contains("price:" + (ad.getPrice().isText() ? ad.getPrice().getStringValue().getText() : ad.getPrice().getNumericValue())));
            }

            SearchAdResponse response = new SearchAdResponse();
            response.setAds(filteredLines.toList());

            return Optional.of(response);
        }
        catch (IOException e){
            return Optional.empty();
        }
    }
}
