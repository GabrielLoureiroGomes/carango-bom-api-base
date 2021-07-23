package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String name);

    Optional<User> create(User user);

    void changePassword(Long id, String password);

    void delete(Long id);
}
