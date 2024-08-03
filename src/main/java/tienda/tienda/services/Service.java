package tienda.tienda.services;

import java.util.List;
import java.util.Optional;

// T -> Entidad
// W -> Id
public interface Service <T, W>{
    T add(T entity);

    Optional<T> getById(W id);

    List<T> getAll();

    void delete(W id);

    T update(T entity, W id);


}
