package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.requests.CreateUpdateUserRequest;
import br.com.caelum.carangobom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carangobom/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateUpdateUserRequest request) {
        userService.createUser(request.toUser());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public void changePassword(@PathVariable Long id, @RequestBody CreateUpdateUserRequest request) {
        userService.changePassword(id, request.getPassword());
    }

}
