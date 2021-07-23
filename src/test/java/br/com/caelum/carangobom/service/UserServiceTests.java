package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.exception.UserNotFoundException;
import br.com.caelum.carangobom.mocks.UserMocks;
import br.com.caelum.carangobom.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @MockBean
    private DataSource dataSource;


    @Test
    @DisplayName("FIND ALL USERS")
    void shouldGetAllUsers() {
        given(userRepository.findAll()).willReturn(UserMocks.getListUsers());

        List<User> expected = userService.findAllUsers();

        assertEquals(expected, UserMocks.getListUsers());
        assertEquals(2, expected.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("FIND USER BY ID")
    void testGetUserById() {
        Long id = 1L;
        given(userRepository.findById(any())).willReturn(Optional.of(UserMocks.getArthur()));
        User expected = userService.findUserById(id);
        assertEquals(expected, UserMocks.getArthur());
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("FIND USER BY NON EXISTING ID")
    void shouldThrowErrorWhenGetUserByNonExistingId() {
        Long id = 1L;
        given(userRepository.findById(id)).willReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(id));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("CREATE USER")
    void shouldCreateUser() {
        User gabriel = UserMocks.getGabriel();

        given(userRepository.findByName(any())).willReturn(Optional.empty());
        given(userRepository.create(any())).willReturn(Optional.of(gabriel));

        userService.createUser(gabriel);

        verify(userRepository, times(1)).create(any());
    }


    @Test
    @DisplayName("CHANGE PASSWORD")
    void shouldChangePassword() {
        Long id = 2L;
        String password = UserMocks.getGabriel().getPassword();

        given(userRepository.findById(id)).willReturn(Optional.of(UserMocks.getGabriel()));

        userService.changePassword(id, password);

        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).changePassword(any(), any());
    }

    @Test
    @DisplayName("CHANGE PASSWORD BY NON EXISTING ID")
    void shouldThrowErrorWhenChangePasswordWithNonExistingId() {
        Long id = 2L;
        Optional<User> optionalUser = Optional.of(UserMocks.getArthur());
        String password = optionalUser.get().getPassword();

        given(userRepository.findById(any())).willThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.changePassword(id, password));
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, never()).changePassword(any(), any());
    }

    @Test
    @DisplayName("DELETE USER")
    void shouldDeleteUser() {
        Long id = 4L;

        given(userRepository.findById(any())).willReturn(Optional.of(UserMocks.getGabriel()));

        userService.deleteUser(id);

        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("DELETE BRAND BY NON EXISTING ID")
    void shouldThrowErrorWhenDeleteBrandNonExistingId() {
        Long id = 2L;

        given(userRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(id));
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, never()).delete(any());
    }
}
