package Exercise_Hotels.dao;

import java.util.List;

public interface IDAO <T> {

    List<T> getAll();
    T getById(Long id);
    abstract T create(T entity);
    T update(T entity);
    void delete(T entity);
}
