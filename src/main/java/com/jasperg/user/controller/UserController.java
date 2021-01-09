package com.jasperg.user.controller;

import com.jasperg.user.model.User;
import com.jasperg.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

//    @PostConstruct
//    public void fillDB() {
//        if(userRepository.count() == 0) {
//            userRepository.save(new User("Jasper", "Guldentops", "jg@gmail.com", "0000"));
//            userRepository.save(new User("Andre", "Arboon", "aa@gmail.com", "0000"));
//            userRepository.save(new User("Barry", "Bakker", "bb@gmail.com", "0000"));
//            userRepository.save(new User("Conny", "Cunters", "cc@gmail.com", "0000"));
//
//        }
//    }

    @GetMapping("/users")
    public List<User> getUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/users/email/{email}")
    public List<User> getUsersByEmail(@PathVariable String email) {

        return userRepository.findUsersByEmailContaining(email);
    }

    @GetMapping("/users/name/{name}")
    public List<User> getUsersByName(@PathVariable String name) {

        return userRepository.findUsersByFirstNameContainingOrLastNameContaining(name, name);
    }

    @GetMapping("/users/code/{code}")
    public User getUserByCode(@PathVariable String code) {

        return userRepository.findUserByCode(code);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user){

        userRepository.save(user);

        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User updatedUser){
        User user = userRepository.findUserByCode(updatedUser.getCode());

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        //Code van user niet veranderen, want dan klopt de link met recepten niet meer!
        user.setEmail(updatedUser.getEmail());

        userRepository.save(user);

        return user;
    }

    @DeleteMapping("/users/{code}")
    public ResponseEntity deleteUser(@PathVariable String code){
        User user = userRepository.findUserByCode(code);
        if(user!=null){
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
