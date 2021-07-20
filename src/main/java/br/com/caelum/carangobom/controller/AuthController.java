package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.dto.LoginDTO;
import br.com.caelum.carangobom.controller.dto.TokenDTO;
import br.com.caelum.carangobom.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/carangobom/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Valid LoginDTO login) {
        UsernamePasswordAuthenticationToken authData = login.getAuthData();

        try {
            Authentication authentication = authenticationManager.authenticate(authData);
            String token = tokenService.createToken(authentication);
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
