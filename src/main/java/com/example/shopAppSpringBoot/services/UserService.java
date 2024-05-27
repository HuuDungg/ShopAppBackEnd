package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.conponents.JwtTokenUtil;
import com.example.shopAppSpringBoot.dtos.UserDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.Role;
import com.example.shopAppSpringBoot.models.User;
import com.example.shopAppSpringBoot.reposotories.RoleRepository;
import com.example.shopAppSpringBoot.reposotories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        //kiem tra xem trung so dien thoai khong
        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
            throw new ArithmeticException("user phone number already exists");
        }
        //convert tu dto sang
        User user = User.builder()
                .fullName(userDTO.getFullname())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDatOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();

        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(
                () -> new DataNotFoundException("not found role id")
        );
        user.setRole(role);
        if (userDTO.getGoogleAccountId() == 0 && userDTO.getFacebookAccountId() == 0){
            String  password = userDTO.getPassword();
            String passwordEnCoder = passwordEncoder.encode(password);
            user.setPassword( passwordEnCoder);
        }

        //luu lai data
        userRepository.save(user);

        return user;
    }

    @Override
    public String login(String phoneNumber, String password) throws DataNotFoundException {
       Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
       if (optionalUser.isEmpty()){
            throw new DataNotFoundException("user or password is not valid");
       }

       User userExisting = optionalUser.get();

       if (userExisting.getFacebookAccountId() == 0 && userExisting.getGoogleAccountId() == 0){
           if (!passwordEncoder.matches(password, userExisting.getPassword())){
                throw new BadCredentialsException("wrong phonenumber or password");
           }
       }

       //check password and username by UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            phoneNumber, password
        );

        //authentication with java spring
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

       return jwtTokenUtil.generateToken(userExisting);
    }


}
