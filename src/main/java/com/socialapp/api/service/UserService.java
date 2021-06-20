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
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }


    public User findUserById(String id){
        return userRepository.findById(id).orElseThrow();
    }


    public List<User> findUserFriendsByUserId(String id){
        List<String> userFriendsUIDList = userRepository.findById(id).orElseThrow().getFriendsList();
        List<User> userFriendList = new ArrayList<>();

        userFriendsUIDList.forEach( friend -> userFriendList.add(userRepository.findById(friend).orElseThrow()));

        return userFriendList;
    }

    public String addFriendToUserById(String id, String idAddedUser){
        User user = userRepository.findById(id).orElseThrow();
        AtomicBoolean duplicated = new AtomicBoolean(false);
        List<String> userFriendsList;

        if(user.getFriendsList()!=null){
            userFriendsList = user.getFriendsList();
            userFriendsList.forEach(friend -> {
                if (friend.equals(userRepository.findById(idAddedUser).orElseThrow().getId())){
                    duplicated.set(true);
                    System.out.println("Test");
                }
            });
            if (!duplicated.get()){
                userFriendsList.add(userRepository.findById(idAddedUser).orElseThrow().getId());
                user.setFriendsList(userFriendsList);
                userRepository.save(user);
                return user.getId();
            }else{
                return "Users exists in user friends list";
            }

        }else {
            userFriendsList = new ArrayList<>();
            userFriendsList.add(userRepository.findById(idAddedUser).orElseThrow().getId());
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

    public List<User> findRecentUsers(String id){
        List<String> userFriendsList = findUserById(id).getFriendsList();
        List<User> allUsers = userRepository.findAll();
        List<User> recentUsers = new ArrayList<>();

        if (userFriendsList != null && userFriendsList.size()>0){
            userFriendsList.forEach(friend -> allUsers.forEach(user -> {
                if (!friend.equals(user.getId())&& recentUsers.size() < 10 && !id.equals(user.getId())){
                    if (recentUsers.size() > 0){
                        recentUsers.forEach(recent -> {
                            if (!recent.getId().equals(user.getId())){
                                recentUsers.add(user);
                            }
                        });
                    }
                   else{
                       recentUsers.add(user);
                    }
                }
            }));
        }else{
            allUsers.forEach(user -> {
               if (recentUsers.size() <10 && !id.equals(user.getId())){
                   recentUsers.add(user);
               }
            });
        }
        return recentUsers;
    }

    public String changeUserStatus(String id, String status){
        User user = userRepository.findById(id).orElseThrow();
        user.setStatus(status);
        userRepository.save(user);
        return status;
    }

    public String getUserStatus(String id){
        return userRepository.findById(id).orElseThrow().getStatus();
    }

}
