package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById();

    User create(User user);

    User update(User user);

    void delete(Long id);
}
