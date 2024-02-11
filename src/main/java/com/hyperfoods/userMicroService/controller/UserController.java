package com.hyperfoods.userMicroService.controller;

import com.hyperfoods.userMicroService.dto.CreateAddressDTO;
import com.hyperfoods.userMicroService.dto.CreateUserDTO;
import com.hyperfoods.userMicroService.dto.UpdateUserDTO;
import com.hyperfoods.userMicroService.dto.UserListDTO;
import com.hyperfoods.userMicroService.entity.Address;
import com.hyperfoods.userMicroService.entity.User;
import com.hyperfoods.userMicroService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CreateUserDTO data, UriComponentsBuilder uriBuilder) {
        var user = service.save(new User(data));
        var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }



    @GetMapping
    public ResponseEntity<Page<UserListDTO>> getAll(@PageableDefault(sort = {"name"}, size = 5) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable).map(UserListDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return ResponseEntity.ok(new UserListDTO(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid UpdateUserDTO data) {
        return ResponseEntity.ok(new UserListDTO(service.update(new User(data), id)));
    }

    @PostMapping("/{id}/address")
    public ResponseEntity addAddress(@PathVariable Long id, @RequestBody @Valid CreateAddressDTO data) {

        return ResponseEntity.ok(new CreateAddressDTO(service.addAddress(id, new Address(data))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deactivateById(@PathVariable Long id) {
        service.deactivateById(id);
        return ResponseEntity.noContent().build();
    }
}
