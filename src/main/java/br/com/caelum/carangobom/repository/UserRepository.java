package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void changePassword(Long id, String password);

    void create(User user);

    void delete(Long id);
}
