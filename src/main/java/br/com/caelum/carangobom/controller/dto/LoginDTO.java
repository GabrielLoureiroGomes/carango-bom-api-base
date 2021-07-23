package br.com.caelum.carangobom.controller.dto;

import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Setter
public class LoginDTO {

    private String username;
    private String password;

    public UsernamePasswordAuthenticationToken getAuthData() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
