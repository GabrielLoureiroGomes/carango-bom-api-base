package br.com.caelum.carangobom.requests;

import br.com.caelum.carangobom.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUpdateUserRequest {

    private String username;
    private String password;

    public User toUser() {
        return new User(
                null,
                username,
                password,
                null,
                null,
                null
        );
    }
}
