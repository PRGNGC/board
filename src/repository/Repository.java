package repository;
import entity.User;
import java.util.Optional;

public interface Repository<T, ID> {
    public void save(T entity);
    public Optional<User> findById(ID id);
}
