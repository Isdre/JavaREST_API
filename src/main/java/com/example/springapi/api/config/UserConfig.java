package com.example.springapi.api.config;

import com.example.springapi.api.model.User;
import com.example.springapi.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User a = new User("AAAA", LocalDate.of(2001, Month.JANUARY,31));
            User b = new User("BBBB", LocalDate.of(2003, Month.JANUARY,31));
            User c = new User("CCCC", LocalDate.of(2004, Month.JANUARY,31));
            User d = new User("DDDD", LocalDate.of(2000, Month.JANUARY,31));
            User e = new User("EEEE", LocalDate.of(2002, Month.AUGUST,31));

            userRepository.saveAll(List.of(a,b,c,d,e));
        };


    }
}
