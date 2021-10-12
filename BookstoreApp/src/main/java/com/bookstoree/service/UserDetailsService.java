package com.bookstoree.service;

import com.bookstoree.model.User;
import com.bookstoree.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailsService(UsersRepository userRepository) {
        this.usersRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = usersRepository.findByEmail(username);

        if (byUsername == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CurrentUser(byUsername);
    }
}
