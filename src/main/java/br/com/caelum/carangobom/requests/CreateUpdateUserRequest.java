package br.com.caelum.carangobom.requests;

import br.com.caelum.carangobom.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUpdateUserRequest {

    private String name;
    private String password;

    public User toUser() {
        return new User(
                null,
                name,
                password,
                null,
                null
        );
    }

}
