package com.socialapp.api.service;

import com.socialapp.api.domain.User;
import com.socialapp.api.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public User register(User user){
        return userRepository.insert(user);
    }

    public String login(User user){

        User findedUser = userRepository.findByEmail(user.getEmail()).orElseThrow();

        if (findedUser != null && findedUser.getPassword().equals(user.getPassword())){
            return Jwts.builder()
                    .setSubject(user.getEmail())
                    .claim("email",user.getEmail())
                    .claim("password",user.getPassword())
                    .claim("id",findedUser.getId())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+ 20000000))
                    .signWith(SignatureAlgorithm.HS512,"asffddfs$%&*".getBytes())
                    .compact() + "UID"+findedUser.getId();
        }else{
            return "Wrong username or password!";
        }
    }

}
