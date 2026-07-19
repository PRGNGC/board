package repository;

import entity.User;
import request.*;
import response.*;
import shared.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class UserRepository implements Repository<User, UUID> {
    @Override
    public void save(User user) {
    }

    @Override
    public Optional<User> findById(UUID id) {
        Path file = Path.of("notes2.txt");

        try (Stream<String> lines = Files.lines(file, Charset.forName("windows-1251"))) {
            List<String> filteredLines = lines.filter(s -> s.contains("id:" + id)).toList();

            if(filteredLines.isEmpty()) return Optional.empty();

            String[] splittedString = filteredLines.getFirst().split("; ");

            User result = new User();
            for (String str : splittedString) {
                String[] strValue = str.split(":");
                if(strValue[0].equals("id")) result.setId(UUID.fromString(strValue[1]));
                if(strValue[0].equals("name")) result.setName(strValue[1]);
                if(strValue[0].equals("login")) result.setLogin(strValue[1]);
                if(strValue[0].equals("role")) result.setRole(UserRoleEnum.fromString(strValue[1]));
                if(strValue[0].equals("state")) result.setState(UserStateEnum.fromString(strValue[1]));
            }

            return Optional.of(result);

        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public Optional<AuthUserResponse> authUser(AuthRequest user){
        Path file = Path.of("notes2.txt");

        try (Stream<String> lines = Files.lines(file, Charset.forName("windows-1251"))) {
            List<String> filteredLines = lines.filter(s -> s.contains("name:" + user.getName()))
                    .filter(s -> s.contains("login:" + user.getLogin()))
                    .toList();

            if (filteredLines.isEmpty()) return Optional.empty();

            String[] splittedString = filteredLines.getFirst().split("; ");

            AuthUserResponse response = new AuthUserResponse();
            for (String str : splittedString) {
                String[] strValue = str.split(":");
                if(strValue[0].equals("id")) response.setId(UUID.fromString(strValue[1]));
                if(strValue[0].equals("name")) response.setName(strValue[1]);
                if(strValue[0].equals("login")) response.setLogin(strValue[1]);
                if(strValue[0].equals("role")) response.setRole(UserRoleEnum.fromString(strValue[1]));
                if(strValue[0].equals("state")) response.setState(UserStateEnum.fromString(strValue[1]));
            }

            return Optional.of(response);
        }
        catch (IOException e){
            return Optional.empty();
        }
    }

    public Optional<RegUserResponse> regUser(RegRequest user){
        UUID uuid = UUID.randomUUID();
        UserRoleEnum role = UserRoleEnum.USER;
        UserStateEnum state = UserStateEnum.ACTIVE;

        User newUserEntity = new User();
        newUserEntity.setId(uuid);
        newUserEntity.setRole(role);
        newUserEntity.setState(state);
        newUserEntity.setName(user.getName());
        newUserEntity.setLogin(user.getLogin());

        try(FileWriter writer = new FileWriter("notes2.txt", Charset.forName("windows-1251"), true))
        {
            String record = "id:" + newUserEntity.getId() + "; " + "name:" + newUserEntity.getName() + "; " + "login:" + newUserEntity.getLogin() + "; " + "role:" + newUserEntity.getRole().getText() + "; " + "state:" + newUserEntity.getState().getText() + "; ";
            writer.write(record);
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){
            return Optional.empty();
        }

        RegUserResponse result = new RegUserResponse();
        result.setId(newUserEntity.getId());
        result.setName(user.getName());
        result.setLogin(user.getLogin());
        result.setRole(newUserEntity.getRole());
        result.setState(newUserEntity.getState());

        return Optional.of(result);
    }

    public Optional<FindUserResponse> findUser(FindUserRequest user){
        Optional<User> findResult = this.findById(user.getId());

        if(findResult.isEmpty()) return Optional.empty();

        User foundedUser = findResult.get();

        FindUserResponse result = new FindUserResponse();
        result.setId(foundedUser.getId());
        result.setLogin(foundedUser.getLogin());
        result.setName(foundedUser.getName());
        result.setRole(foundedUser.getRole());
        result.setState(foundedUser.getState());

        return Optional.of(result);
    }

    public Optional<ToggleUserResponse> toggleUser(ToggleUserRequest user){
        Path path = Paths.get("notes2.txt");

        try {
            List<String> lines = Files.readAllLines(path, Charset.forName("windows-1251"));

            String linesToEdit = "";
            int i = 0;
            for (String line : lines) {
                if(line.contains("id:" + user.getId())){
                    linesToEdit = line;
                    break;
                } else {
                    i++;
                }
            }

            String[] splittedString = linesToEdit.split("; ");

            User result = new User();
            for (String str : splittedString) {
                String[] strValue = str.split(":");
                if(strValue[0].equals("id")) result.setId(UUID.fromString(strValue[1]));
                if(strValue[0].equals("name")) result.setName(strValue[1]);
                if(strValue[0].equals("login")) result.setLogin(strValue[1]);
                if(strValue[0].equals("role")) result.setRole(UserRoleEnum.fromString(strValue[1]));
                if(strValue[0].equals("state")) result.setState(UserStateEnum.fromString(strValue[1]));
            }

            result.setState(user.getState());

            String record = "id:" + result.getId() + "; " +
                    "name:" + result.getName() + "; " +
                    "login:" + result.getLogin() + "; " +
                    "role:" + result.getRole().getText() + "; " +
                    "state:" + result.getState().getText() + "; ";

            lines.set(i, record);
            Files.write(path, lines, Charset.forName("windows-1251"));

            ToggleUserResponse response = new ToggleUserResponse();
            response.setId(result.getId());
            response.setName(result.getName());
            response.setLogin(result.getLogin());
            response.setRole(result.getRole());
            response.setState(result.getState());

            return Optional.of(response);
        }
        catch (IOException e) {
            return Optional.empty();
        }
    }
}
