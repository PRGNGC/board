package repository;

public interface Repository<T, ID> {
    public void save(T entity);
    public int findById(ID id);
}
