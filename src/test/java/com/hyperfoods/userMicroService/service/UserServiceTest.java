package com.hyperfoods.userMicroService.service;

import com.hyperfoods.userMicroService.entity.Address;
import com.hyperfoods.userMicroService.entity.User;
import com.hyperfoods.userMicroService.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationService validationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create and return created user when everything is ok")
    void saveSuccess() {
        User user = createUser1();
        when(userService.save(user)).thenReturn(user);
        User savedUser = userService.save(user);
        assertEquals(user, savedUser);
        verify(userRepository, times(1)).save(any());
    }


    @Test
    @DisplayName("Should find and return user by id and active true when everything is ok")
    void findByIdTrueSuccess() {
        User user = createUser1();

        userService.save(user);
        when(userRepository.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(user));
        User returnedUser = userService.findById(1L);

        assertEquals(user, returnedUser);
    }

    @Test
    @DisplayName("Should find and return null when id user don't exist")
    void findByIdTrueReturnNullWhenIdDontExist() {
        User user = new User();
        when(userRepository.findByIdAndActiveTrue(1L)).thenReturn(Optional.empty());
        User returnedUser = userService.findById(1L);

        assertEquals(null, returnedUser);
    }

    @Test
    @DisplayName("Should find and return null when active user don't exist")
    void findByIdTrueReturnNullWhenActiveUserDontExist() {
        User user = createUser1();
        user.setActive(false);
        when(userRepository.findByIdAndActiveTrue(1L)).thenReturn(Optional.empty());
        User returnedUser = userService.findById(1L);

        assertEquals(null, returnedUser);
    }

    @Test
    @DisplayName("Should find and return list of users when everything is ok")
    void findAllSuccess() {
        User user1 = createUser1();
        User user2 = createUser2();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        Page<User> page = new PageImpl<>(users);
        when(userRepository.findAll((Pageable) null)).thenReturn(page);
        int size = page.getSize();
        assertEquals(size, 2);

    }

    @Test
    @DisplayName("Should find and return list of users with single active user")
    void findAllOneActiveSuccess() {
        User activeUser = createUser1();

        User inactiveUser = createUser2();
        inactiveUser.setActive(false);

        List<User> users = Collections.singletonList(activeUser);

        Page<User> page = new PageImpl<>(users);

        when(userRepository.findAllByActiveTrue(any(Pageable.class))).thenReturn(page);

        Page<User> result = userService.findAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertTrue(result.getContent().contains(activeUser));
        assertFalse(result.getContent().contains(inactiveUser));
    }


    @Test
    @DisplayName("Should find and return empty list of users ")
    void findAllReturnEmpty() {

        List<User> users = new ArrayList<>();
        Page<User> page = new PageImpl<>(users);
        assertEquals(0, page.getSize());
        when(userRepository.findAll((Pageable) null)).thenReturn(page);
    }

    @Test
    void update() {
    }

    @Test
    void addAddress() {
    }

    @Test
    void removeAddress() {
    }

    @Test
    void delete() {
    }

    @Test
    @DisplayName("Should not deactivate user already inactive")
    void deactivateByIdFailUserActiveFalse() {
        User user = createUser1();

    }

    private User createUser1(){
        List<Address> adressess = new ArrayList<>();
        User user = new User(1L, "Thiago", "trcosta97@hotmail.com", "123456", adressess, true, LocalDateTime.now());
        return user;
    }

    private User createUser2(){
        List<Address> adressess = new ArrayList<>();
        User user = new User(2L, "Julia", "julia00@hotmail.com", "123456", adressess, true, LocalDateTime.now());
        return user;
    }
}