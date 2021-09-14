package com.thevirtugroup.postitnote.repository;


import com.thevirtugroup.postitnote.model.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepository() {
        populateUsers();
    }

    public User findUserByUsername(String username){
        return users.stream().filter(user -> username.equals(user.getUsername())).findAny().orElse(null);
    }

    public User findById(Long id) {
        return users.stream().filter(user -> id.equals(user.getId())).findAny().orElse(null);
    }

    private void populateUsers(){
        users.add(new User(1L, "Johnny", "password1", "Johnny Tango"));
        users.add(new User(2L, "Ria", "password2", "Ria Brown"));
        users.add(new User(3L, "Robert", "password3", "Robert Lara"));
    }
}
