package com.example.demo.request;
import lombok.Data;
@Data
public class UserRequest {
    private String password;
    private String name;
    private String surname;
    private String email;
    private Long phoneNumber;
    private String address;
}