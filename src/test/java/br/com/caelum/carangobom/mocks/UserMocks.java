package br.com.caelum.carangobom.mocks;

import br.com.caelum.carangobom.domain.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class UserMocks {

    public static User getArthur() {
        User user = new User();
        user.setId(1L);
        user.setName("Arthur");
        user.setPassword("12345");
        user.setCreatedAt(LocalDate.now().plusDays(2));
        user.setUpdatedAt(null);
        return user;
    }

    public static User getGabriel() {
        User user = new User();
        user.setId(2L);
        user.setName("Gabriel");
        user.setPassword("67890");
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(null);
        return user;
    }

    public static List<User> getListUsers() {
        return Arrays.asList(getArthur(), getGabriel());
    }

}
