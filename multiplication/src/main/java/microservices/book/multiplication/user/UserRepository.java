package microservices.book.multiplication.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByAlias(String alias);
    List<User> findAllByIdIn(final List<Long> ids);
}