package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.dtos.UserDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;

    String login(String phoneNumber, String password) throws DataNotFoundException;

}
