package com.socialapp.api.service;

import com.socialapp.api.domain.User;
import com.socialapp.api.repository.UserRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public String insertUser(User user){
        userRepository.insert(user);
        return user.getId();
    }

    public User findUserById(String id){
        return userRepository.findById(id).orElseThrow();
    }

    public User login(String email, String password){
        return userRepository.findUserByEmailAndPassword(email,password).orElseThrow();
    }

    public List<User> findUserFriendsByUserId(String id){
        return userRepository.findById(id).orElseThrow().getFriendsList();
    }

    public String addFriendToUserById(String id, String idAddedUser){
        User user = userRepository.findById(id).orElseThrow();
        List<User> userFriendsList;
        if(user.getFriendsList()!=null){
            userFriendsList = user.getFriendsList();
            userFriendsList.add(userRepository.findById(idAddedUser).orElseThrow());
            user.setFriendsList(userFriendsList);
            userRepository.save(user);
            return user.getId();
        }else {
            user.setFriendsList(new ArrayList<>());
            userFriendsList = user.getFriendsList();
            userFriendsList.add(userRepository.findById(idAddedUser).orElseThrow());
            user.setFriendsList(userFriendsList);
            userRepository.save(user);
            return user.getId();
        }
    }

    public String changeProfilePhoto(String id,MultipartFile file) throws IOException{
        User user = userRepository.findById(id).orElseThrow();
        user.setProfileImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        userRepository.save(user);
        return user.getId();
    }

    public void delete(String id){
        userRepository.deleteById(id);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }


}
