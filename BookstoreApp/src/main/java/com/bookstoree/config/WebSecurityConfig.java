package com.bookstoree.config;

import com.bookstoree.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/logIn").permitAll()
                .usernameParameter("email")
                .defaultSuccessUrl("/bookslist")
                .loginProcessingUrl("/logIn")
                .and()
                .logout()
                .logoutSuccessUrl("/logIn")
                .and()
                .authorizeRequests()
                .antMatchers("/regUser").permitAll()
                .antMatchers("/logIn").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/register").hasAuthority("ADMIN")
//                .antMatchers("/bookslist").hasAnyAuthority("ADMIN")
                .antMatchers("/admin/").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/booklist").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/singlebook").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
