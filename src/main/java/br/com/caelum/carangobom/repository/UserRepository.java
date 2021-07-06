package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    User create(User user);

    User update(User user);

    void delete(Long id);
}
