package com.apiSpring.agregadordeinvestimentos.services;

import com.apiSpring.agregadordeinvestimentos.DTOs.CreateUserDto;
import com.apiSpring.agregadordeinvestimentos.DTOs.UpdateUserDTO;
import com.apiSpring.agregadordeinvestimentos.entity.User;
import com.apiSpring.agregadordeinvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser{

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateUser(){

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "testeUnitario",
                    "testeUnitario@email.com",
                    "123",
                    Instant.now(),
                    null

            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto("testeUnitario", "testeUnitario@email.com", "123");

            //Act
            var output = userService.createUser(input);

            //Assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUserName());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());


        }

        @Test
        @DisplayName("Should throws a exception when a error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            //Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto("testeUnitario", "testeUnitario@email.com", "123");

            //Act & Assert
             assertThrows(RuntimeException.class, () -> userService.createUser(input));

        }
    }

    @Nested
    class getUserById {
        @Test
        @DisplayName("Should get user by id with success when optional is present")
        void shouldGetUserByIdWithSuccess() {

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "testeUnitario",
                    "testeUnitario@email.com",
                    "123",
                    Instant.now(),
                    null

            );
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(user.getUserId().toString());

            //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id with success when optional is empty")
        void shouldThrowErrorWhenOptionalIsEmpty() {
            //Arrange
            var userId = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(userId.toString());

            //Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class getUsers{
        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUsersWithSuccess() {

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "testeUnitario",
                    "testeUnitario@email.com",
                    "123",
                    Instant.now(),
                    null

            );
            var userList = List.of(user);
            doReturn(userList)
                    .when(userRepository)
                    .findAll();

            //Act
            var output = userService.getUsers();

            //Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());

        }
    }

    @Nested
    class deleteUser{
        @Test
        @DisplayName("Should delete a user by ID with success when user exists")
        void shouldDeleteUserByIdWithSuccessWhenUserExist(){
            //Arrange
            doReturn(true)
                    .when(userRepository)
                            .existsById(uuidArgumentCaptor.capture());

            doNothing()
                    .when(userRepository)
                            .deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();
            //Act
            userService.deleteUser(userId.toString());

            //Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));

        }

        @Test
        @DisplayName("Should not delete a user when user not exists")
        void shouldNotDeleteUserWhenUserNotExists(){
            //Arrange
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();
            //Act

            userService.deleteUser(userId.toString());

            //Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(any());

        }
    }

    @Nested
    class updateUserById{
        @Test
        @DisplayName("Should update a user when user exists and the password and the username is filled")
        void shouldCreateUserWhenExistAndPassWordAndUsernameIsFilled() {
            //Arrange
            var updateUserDto = new UpdateUserDTO(
                    "NewUserName",
                    "NewPassWord"
            );
            var user = new User(
                    UUID.randomUUID(),
                    "testeUnitario",
                    "testeUnitario@email.com",
                    "123",
                    Instant.now(),
                    null

            );
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            //Act
            userService.updateUserById(user.getUserId().toString(), updateUserDto);

            //Assert

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(updateUserDto.username(), userCaptured.getUserName());
            assertEquals(updateUserDto.password(), userCaptured.getPassword());

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.capture());
            verify(userRepository, times(1)).save(user);
        }

        @Test
        @DisplayName("Should not update a user when the user not exists")
        void shouldNotUpdateUserWhenUserNotExists() {
            //Arrange
            var updateUserDto = new UpdateUserDTO(
                    "NewUserName",
                    "NewPassWord"
            );

            var userId = UUID.randomUUID();

            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //Act
            userService.updateUserById(userId.toString(), updateUserDto);

            //Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.capture());
            verify(userRepository, times(0)).save(any());
        }
    }
}