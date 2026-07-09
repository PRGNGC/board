package repository;
import entity.User;

public class UserRepository implements Repository<User, Integer> {
    @Override
    public void save(User user) {
    }

    @Override
    public int findById(Integer id) {
        return 0;
    }
}
