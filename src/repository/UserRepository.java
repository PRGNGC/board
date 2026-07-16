package repository;
import entity.User;
import request.CreateUserRequest;
import response.UserResponse;
import java.util.Optional;

public class UserRepository implements Repository<User, Integer> {
    @Override
    public void save(User user) {
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(null);
    }

    public UserResponse createAd(CreateUserRequest user){
        //        UUID uuid = UUID.randomUUID();
        //        this.id = uuid;
        return new UserResponse("", "");
    }
}
