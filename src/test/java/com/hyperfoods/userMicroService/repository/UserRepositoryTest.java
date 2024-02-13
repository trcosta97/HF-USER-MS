package com.hyperfoods.userMicroService.repository;

import com.hyperfoods.userMicroService.dto.CreateUserDTO;
import com.hyperfoods.userMicroService.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;


    @Test
    void findAll() {
    }

    @Test
    @DisplayName("Should find User by id and active = true success")
    void findByIdAndActiveTrueSuccess() {
        CreateUserDTO data = new CreateUserDTO("Thiago", "trcosta97@gmail.com", "123456");
        this.createUser(data);

        Optional<User> result = userRepository.findByIdAndActiveTrue(1L);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not find User by id and active = true success")
    void findByIdAndActiveTrueFail() {

        Optional<User> result = userRepository.findByIdAndActiveTrue(1L);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should find list with > 0 users active = true success")
    void findAllByActiveTrueWithContent() {
        CreateUserDTO data = new CreateUserDTO("Thiago", "trcosta97@gmail.com", "123456");
        CreateUserDTO data2 = new CreateUserDTO("Julia", "julia00@gmail.com", "8793127");

        this.createUser(data);
        this.createUser(data2);

        List<User> result = userRepository.findAllByActiveTrue(null).getContent();

        assertThat(result.size() > 0).isTrue();

    }



    private User createUser(CreateUserDTO data) {
        User user1 = new User(data);
        user1.setActive(true);
        this.entityManager.persist(user1);
        return user1;
    }
}