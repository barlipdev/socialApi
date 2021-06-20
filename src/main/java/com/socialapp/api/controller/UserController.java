package com.socialapp.api.controller;

import com.socialapp.api.domain.User;
import com.socialapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id){
        return userService.findUserById(id);
    }

    @PostMapping("/users/friends")
    public String addFriend(@RequestParam("id") String id,@RequestParam("idAdded") String idAdded, Model model){
        return userService.addFriendToUserById(id,idAdded);
    }

    @GetMapping("/users/friends/{id}")
    public List<User> getFriends(@PathVariable String id){
        return userService.findUserFriendsByUserId(id);
    }

    @PostMapping("/users/photo")
    public String changePhoto(@RequestParam("id") String id, @RequestParam("profileImage")MultipartFile profileImage, Model model) throws IOException {
        return userService.changeProfilePhoto(id,profileImage);
    }

    @GetMapping("/users/photo/{id}")
    public String getPhoto(@PathVariable String id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("profilePhoto", Base64.getEncoder().encodeToString(user.getProfileImage().getData()));
        return user.getId();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable String id){
        userService.delete(id);
        return "Deleted user with id: "+id;
    }

    @DeleteMapping("/users")
    public String deleteAll(){
        userService.deleteAll();
        return "Deleted all users!";
    }

    @GetMapping("/users/recent/{id}")
    public List<User> findRecentUsers(@PathVariable String id){
        return userService.findRecentUsers(id);
    }

    @PostMapping("/users/status")
    public String changeUserStatus(@RequestParam("id") String id,@RequestParam("status") String status){
        return userService.changeUserStatus(id,status);
    }

    @GetMapping("/users/status/{id}")
    public String getUserStatus(@PathVariable String id){
        return userService.getUserStatus(id);
    }
}
