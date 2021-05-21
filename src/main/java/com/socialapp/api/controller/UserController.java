package com.socialapp.api.controller;

import com.socialapp.api.domain.LoginData;
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

    @PostMapping("/users/register")
    public String register(@RequestBody User user){
        return userService.insertUser(user);
    }

    @GetMapping("/users/login")
    public User login(@RequestBody LoginData loginData){
        return userService.login(loginData.getEmail(),loginData.getPassword());
    }

    @PostMapping("/users/friends/{id}&{idAdded}")
    public String addFriend(@PathVariable String id,@PathVariable String idAdded){
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

}
