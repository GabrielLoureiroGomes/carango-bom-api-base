package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @InjectMocks
    private UserRepository userRepository;

    @Test
    void testShouldLoadUserByUsername() {
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(new User()));
        Optional<User> user = assertDoesNotThrow(() -> userRepository.findByUsername(Mockito.anyString()));
    }

}