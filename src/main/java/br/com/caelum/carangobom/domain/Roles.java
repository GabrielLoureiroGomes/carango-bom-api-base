package br.com.caelum.carangobom.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class Roles implements GrantedAuthority {

    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
