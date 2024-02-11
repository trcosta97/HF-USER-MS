package com.hyperfoods.userMicroService.dto;

import com.hyperfoods.userMicroService.entity.User;

public record UserListDTO(
        Long id,
        String name,
        String email
) {

    public UserListDTO(User user){
        this(user.getId(), user.getName(), user.getEmail());
    }
}
