package com.banco.Saint_Patrik;

import com.banco.Saint_Patrik.Entity.UserEntity;
import com.banco.Saint_Patrik.Error.ErrorService;
import com.banco.Saint_Patrik.Repository.UserRepository;
import com.banco.Saint_Patrik.Service.CardService;
import com.banco.Saint_Patrik.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SaintPatrikApplication {

    @Autowired
    private CardService cardService;
    
    static UserRepository uRepo;
    

    public static void main(String[] args) throws ErrorService {
        SpringApplication.run(SaintPatrikApplication.class, args);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(cardService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
