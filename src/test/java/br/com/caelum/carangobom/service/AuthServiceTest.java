package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.exception.BrandNotFoundException;
import br.com.caelum.carangobom.exception.UserNotFoundException;
import br.com.caelum.carangobom.mocks.UserMocks;
import br.com.caelum.carangobom.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void getMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("LOAD USER BY USERNAME")
    void testShouldLoadUserByUsername() {
        String username = "gabriel";
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(UserMocks.getGabriel()));

        User expected = userService.findUserByUsername(username);

        assertEquals(expected, UserMocks.getGabriel());
        verify(userRepository, times(1)).findByUsername(any());
    }

    @Test
    @DisplayName("LOAD USER BY NON EXISTING USERNAME")
    void testShouldThrowErrorWhenTryLoadUserByUsername() {
        String username = "gabriel";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(any());
    }

}