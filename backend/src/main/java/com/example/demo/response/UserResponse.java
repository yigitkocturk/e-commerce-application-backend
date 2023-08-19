package com.example.demo.response;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class UserResponse {
    Long id;
    String name;
    String surname;
    String email;
    Long phoneNumber;
    String address;
    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
    }
}

