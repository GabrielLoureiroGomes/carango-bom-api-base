package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.exception.BusinessException;
import br.com.caelum.carangobom.exception.UserDuplicatedException;
import br.com.caelum.carangobom.exception.UserNotFoundException;
import br.com.caelum.carangobom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        Optional<User> result = userRepository.findById(id);

        return result.orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    public User createUser(User user) {
        Optional<User> result = userRepository.findByUsername(user.getUsername());

        if (result.isPresent()) throw new UserDuplicatedException(user.getUsername());

        return userRepository.create(user).orElseThrow(BusinessException::new);
    }

    public void changePassword(Long id, String password) {
        findUserById(id);
        userRepository.changePassword(id, password);
    }

    public void deleteUser(Long id) {
        findUserById(id);
        userRepository.delete(id);
    }

}