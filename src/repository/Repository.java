package repository;
import shared.SaveMethodsEnum;

import java.io.IOException;
import java.util.Optional;

public interface Repository<T, ID> {
    public void save(T entity) throws IOException;
    public Optional<T> findById(ID ad);
}
