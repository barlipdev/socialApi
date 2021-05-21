package com.socialapp.api.domain;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Users")
public class User {


    @Id
    private String id;
    private String email;
    private String name;
    private String surname;
    private String password;
    private String status;
    private Binary profileImage;
    @DBRef
    private List<User> friendsList;

    public User() {
    }

    public User(String id, String email, String name, String surname, String password, String status, Binary profileImage, List<User> friendsList) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.status = status;
        this.profileImage = profileImage;
        this.friendsList = friendsList;
    }

    public User(String email, String name, String surname, String password, String status, Binary profileImage, List<User> friendsList) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.status = status;
        this.profileImage = profileImage;
        this.friendsList = friendsList;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Binary getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Binary profileImage) {
        this.profileImage = profileImage;
    }

    public List<User> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<User> friendsList) {
        this.friendsList = friendsList;
    }
}
