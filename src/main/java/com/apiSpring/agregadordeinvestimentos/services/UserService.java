package com.apiSpring.agregadordeinvestimentos.services;

import com.apiSpring.agregadordeinvestimentos.DTOs.CreateAccountDto;
import com.apiSpring.agregadordeinvestimentos.DTOs.CreateUserDto;
import com.apiSpring.agregadordeinvestimentos.DTOs.UpdateUserDTO;
import com.apiSpring.agregadordeinvestimentos.entity.Account;
import com.apiSpring.agregadordeinvestimentos.entity.BillingAddress;
import com.apiSpring.agregadordeinvestimentos.entity.User;
import com.apiSpring.agregadordeinvestimentos.repository.AccountRepository;
import com.apiSpring.agregadordeinvestimentos.repository.BillingAddressRepository;
import com.apiSpring.agregadordeinvestimentos.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private AccountRepository accountRepository;
    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }


    public UUID createUser(CreateUserDto createUserDto){
        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null
        );
        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(String userId){
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if(userExists){
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(String userId, UpdateUserDTO updateUserDTO){
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if(userEntity.isPresent()){
               var user = userEntity.get();

               if(updateUserDTO.username() != null){
                   user.setUserName(updateUserDTO.username());
               }

               if(updateUserDTO.password() != null){
                   user.setPassword(updateUserDTO.password());
               }

               userRepository.save(user);
        }
    }


    public void createAccount(String userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //DTO -> ENTITY

        var account = new Account(
                UUID.randomUUID(),
                user,
                null,
                createAccountDto.description(),
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                createAccountDto.street(),
                createAccountDto.number()
        );

        billingAddressRepository.save(billingAddress);


    }
}
