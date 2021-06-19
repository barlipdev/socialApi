package com.socialapp.api.domain;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Document(collection = "Users")
public class User {


    @Id
    private String id;
    @Indexed(unique=true)
    private String email;
    private String name;
    private String surname;
    private String password;
    private String status;
    private Binary profileImage;
    private List<String> friendsList;

    public User() {
    }

    public User(String id, String email, String name, String surname, String password, String status, List<String> friendsList) throws IOException {
        URL imageURL = new URL("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png");
        BufferedImage originalImage= ImageIO.read(imageURL);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos );
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.status = status;
        this.profileImage = new Binary(BsonBinarySubType.BINARY, baos.toByteArray());
        this.friendsList = friendsList;
    }


    public User(String email, String name, String surname, String password, String status, List<String> friendsList) throws IOException {
        URL imageURL = new URL("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png");
        BufferedImage originalImage= ImageIO.read(imageURL);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos );
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.status = status;
        this.profileImage = new Binary(BsonBinarySubType.BINARY, baos.toByteArray());
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

    public List<String> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<String> friendsList) {
        this.friendsList = friendsList;
    }
}
