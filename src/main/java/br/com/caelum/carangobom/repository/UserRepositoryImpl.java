package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.of(new User(Long.parseLong("1"), "arthur", "$2a$10$Aw/R99yW3F/XbmOO.IKBpuKzVTj5UJ.dApxJ389aQkS9mhIRFLEhu"));
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
