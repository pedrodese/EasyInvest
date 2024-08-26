package com.apiSpring.agregadordeinvestimentos.controllers;

import com.apiSpring.agregadordeinvestimentos.DTOs.CreateAccountDto;
import com.apiSpring.agregadordeinvestimentos.DTOs.CreateUserDto;
import com.apiSpring.agregadordeinvestimentos.DTOs.UpdateUserDTO;
import com.apiSpring.agregadordeinvestimentos.entity.User;
import com.apiSpring.agregadordeinvestimentos.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Retorna todos os usuarios cadastrados no banco
    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        var users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    //Retorna o usuario pelo Id
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
        var user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Cria Usuarios
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto){
        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    //Atualiza usuario pelo ID
    @PutMapping("{userId}")
    public ResponseEntity<User> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDTO updateUserDTO){
          userService.updateUserById(userId, updateUserDTO);
          return ResponseEntity.noContent().build();
    }


    //EndPoint para deletar o usuario por ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    //EndPoint para criar uma conta vinculado a um user
    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable("userId") String userId,
                                              @RequestBody CreateAccountDto createAccountDto){
        userService.createAccount(userId, createAccountDto);
        return ResponseEntity.ok().build();
    }

}
