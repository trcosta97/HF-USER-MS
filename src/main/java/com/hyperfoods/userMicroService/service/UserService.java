package com.hyperfoods.userMicroService.service;

import com.hyperfoods.userMicroService.entity.Address;
import com.hyperfoods.userMicroService.entity.User;
import com.hyperfoods.userMicroService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ValidationService validationService;

    public User save(User user) {
        return repository.save(user);
    }

    public User findById(Long id) {
        return repository.findByIdAndActiveTrue(id).orElse(null);
    }

    public Page<User> findAll(Pageable pageable) {
        return repository.findAllByActiveTrue(pageable);
    }


    public User update(User user, Long id) {
        User existingUser = repository.findById(id).orElse(null);
        if (user.getName() != null){
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null){
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null){
            existingUser.setPassword(user.getPassword());
        }
        return repository.save(existingUser);
    }

    public Address addAddress(Long id, Address address){
        var user = findById(id);
        user.getAddresses().add(address);
        var savedUser = repository.save(user);
        return savedUser.getAddresses().get(savedUser.getAddresses().size() - 1);
    }

    public void removeAddress(Long id, Long addressId){
        var user = findById(id);
        user.getAddresses().removeIf(address -> address.getId() == addressId);
        repository.save(user);
    }

    public void delete(Long id) throws SQLException {
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getAddresses().size() > 0){
                throw new SQLException("User has addresses");
            }
            repository.delete(user);
            }else{
                throw new SQLException("User not found");
        }
        repository.deleteById(id);
    }

    public void deactivateById(Long id) {
        var user = findById(id);
        if (validationService.canDeactivate(user)) {
            user.setActive(false);
            repository.save(user);
        }
    }
}