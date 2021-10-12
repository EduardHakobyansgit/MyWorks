package com.bookstoree.controller;

import com.bookstoree.model.User;
import com.bookstoree.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    private UsersRepository usersRepository;

//       public void UsersRepository(UsersRepository usersRepository) {
//            this.usersRepository = usersRepository;
//        }

    @PostMapping("/regUser")
    public String registerUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        usersRepository.save(user);
        return ("redirect:/bookslist");
    }

//    @RequestMapping("/bookslist")
//    public String defaultAfterLogin(HttpServletRequest request) {
//        if (request.isUserInRole("ADMIN")) {
//            return "redirect:/bookslist";
//        }
//        return "redirect:/bookslistuser";
//    }

    @GetMapping("/regUser")
    public String regUser() {
        return "regUser";
    }

    @GetMapping("/logIn")
    public String loginPage() {
        return "logIn";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


}
