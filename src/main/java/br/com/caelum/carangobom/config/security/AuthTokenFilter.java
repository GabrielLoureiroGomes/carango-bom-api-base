package br.com.caelum.carangobom.config.security;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.exception.UserNotFoundException;
import br.com.caelum.carangobom.repository.UserRepository;
import br.com.caelum.carangobom.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(httpServletRequest);
        boolean isTokenValid = tokenService.validateToken(token);

        if (isTokenValid) authenticateClient(token);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void authenticateClient(String token) {
        Long userId = tokenService.getUserId(token);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) throw new UserNotFoundException(userId.toString());

        User user = optionalUser.get();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(optionalUser, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}

