package com.hyperfoods.userMicroService.service;

import com.hyperfoods.userMicroService.entity.User;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public boolean canDeactivate(User user){
        if(user.isActive()){
            return true;
        }
            return false;
    }
}
