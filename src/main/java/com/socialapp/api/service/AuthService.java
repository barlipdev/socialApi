package com.socialapp.api.service;

import com.socialapp.api.domain.User;
import com.socialapp.api.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public User register(User user) throws IOException {
        user.setProfileImage(loadDefaultImg("https://st2.depositphotos.com/1104517/11965/v/600/depositphotos_119659092-stock-illustration-male-avatar-profile-picture-vector.jpg"));
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

    private Binary loadDefaultImg(String url) throws IOException {
        URL imageURL = new URL(url);
        BufferedImage originalImage= ImageIO.read(imageURL.openStream());
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos );
        return new Binary(BsonBinarySubType.BINARY, baos.toByteArray());
    }

}
