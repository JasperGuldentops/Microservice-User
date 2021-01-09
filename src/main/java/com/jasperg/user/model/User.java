package com.jasperg.user.model;

import com.jasperg.user.helper.Helper;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    @Column(unique = true)
    private String code;

    public User() {
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.code = email + "-" + Helper.getRandomString(4);
    }

    public User(String firstName, String lastName, String email, String code) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.code = code;
//        this.code = email + "-" + codeString; Causes problems when changing e-mail
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
