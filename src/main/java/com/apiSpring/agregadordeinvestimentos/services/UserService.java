package com.apiSpring.agregadordeinvestimentos.services;

import com.apiSpring.agregadordeinvestimentos.DTOs.CreateUserDto;
import com.apiSpring.agregadordeinvestimentos.DTOs.UpdateUserDTO;
import com.apiSpring.agregadordeinvestimentos.entity.User;
import com.apiSpring.agregadordeinvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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


}
